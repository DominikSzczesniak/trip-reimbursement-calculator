package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementConfigurationService.ReimbursementConfigurationDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.exceptions.LimitsReachedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementRequestService {

	private final ReimbursementConfigurationService reimbursementConfigurationService;

	ReimbursementRequestService(final ReimbursementConfigurationService reimbursementConfigurationService) {
		this.reimbursementConfigurationService = reimbursementConfigurationService;
	}

	public ReimbursementRequestResult submitReimbursementRequest(final SubmitReimbursementRequest command) {
		final ReimbursementRequest reimbursement = new ReimbursementRequest(
				new ReimbursementId(UUID.randomUUID()),
				command.getTripDate(),
				command.getCarMileage().orElse(new CarMileage(0)),
				command.getDaysOfAllowance().orElse(new DaysOfAllowance(0)),
				command.getReceipts().orElse(new ArrayList<>())
		);

		final ReimbursementConfigurationDTO reimbursementConfiguration = reimbursementConfigurationService.getReimbursementConfiguration();
		final Money totalReimbursementAmount = calculateTotalReimbursementAmount(reimbursement, reimbursementConfiguration);

		return new ReimbursementRequestResult(reimbursement.getReimbursementId(), totalReimbursementAmount);
	}

	private Money calculateTotalReimbursementAmount(final ReimbursementRequest request, final ReimbursementConfigurationDTO configuration) {
		final Money carUsageReimbursement = calculateCarUsage(
				request.getCarMileage().get(),
				configuration);

		final Money dailyAllowanceReimbursement = calculateDailyAllowance(
				request.getDaysOfAllowance().get(),
				configuration);

		final Money receiptsReimbursement = calculateReceipts(
				request.getReceipts().get(),
				configuration);

		final Money totalAmount = carUsageReimbursement.add(dailyAllowanceReimbursement).add(receiptsReimbursement);

		checkTotalReimbursementLimit(configuration, totalAmount);

		return totalAmount;
	}

	private Money calculateDailyAllowance(final DaysOfAllowance daysOfAllowance, final ReimbursementConfigurationDTO configuration) {
		if (configuration.getDailyAllowanceRate().isPresent() && daysOfAllowance != null) {
			final String days = String.valueOf(daysOfAllowance.getValue());
			return configuration.getDailyAllowanceRate().get().multiply(days);
		}
		return new Money("0");
	}

	private Money calculateCarUsage(final CarMileage carMileage, final ReimbursementConfigurationDTO configuration) {
		if (configuration.getCarMileageRate().isPresent() && carMileage != null) {
			final String carUsageInKm = String.valueOf(carMileage.getValue());
			return configuration.getCarMileageRate().get().multiply(carUsageInKm);
		}
		return new Money("0");
	}

	private Money calculateReceipts(final List<Receipt> requestedReceipts, final ReimbursementConfigurationDTO configuration) {
			Money amount = new Money("0");
			for (Receipt receipt : requestedReceipts) {
				if (configuration.getReceipts().get().contains(receipt.getReceiptType())) {
					final Money money = receipt.getPrice();
					amount = amount.add(money);
				}
			}
			return amount;
	}

	private static void checkTotalReimbursementLimit(final ReimbursementConfigurationDTO configuration, final Money totalAmount) {
		final BigDecimal amount = totalAmount.getValue();
		if (configuration.getTotalReimbursementLimit().isPresent()) {
			final BigDecimal limit = configuration.getTotalReimbursementLimit().get().getValue();
			if (amount.compareTo(limit) > 0) {
				throw new LimitsReachedException("Reimbursement claim value exceeds limits");
			}
		}
	}

}
