package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementConfigurationService.ReimbursementConfigurationDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReceiptTypeReimbursementLimit;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.exceptions.LimitsReachedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReimbursementRequestService {

	public static final int REIMBURSEMENT_LIMIT = 0;
	private final ReimbursementConfigurationService reimbursementConfigurationService;

	ReimbursementRequestService(final ReimbursementConfigurationService reimbursementConfigurationService) {
		this.reimbursementConfigurationService = reimbursementConfigurationService;
	}

	public ReimbursementRequestResult submitReimbursementRequest(final SubmitReimbursementRequest command) {
		final ReimbursementRequest reimbursementRequest = createReimbursementRequest(command);
		final ReimbursementConfigurationDTO reimbursementConfiguration = reimbursementConfigurationService.getReimbursementConfiguration();

		final Money totalReimbursementAmount = calculateTotalReimbursementAmount(reimbursementRequest, reimbursementConfiguration);
		checkTotalReimbursementLimit(reimbursementConfiguration, totalReimbursementAmount);

		return new ReimbursementRequestResult(reimbursementRequest.getReimbursementId(), totalReimbursementAmount);
	}

	private static ReimbursementRequest createReimbursementRequest(final SubmitReimbursementRequest command) {
		return new ReimbursementRequest(
				command.getId(),
				command.getTripDate(),
				command.getCarMileage().orElse(new CarMileage(0)),
				command.getDaysOfAllowance().orElse(new DaysOfAllowance(0)),
				command.getReceipts().orElse(new ArrayList<>())
		);
	}

	private Money calculateTotalReimbursementAmount(final ReimbursementRequest request, final ReimbursementConfigurationDTO configuration) {
		final Money carUsageReimbursement = calculateCarUsage(request.getCarMileage().get(), configuration);
		final Money dailyAllowanceReimbursement = calculateDailyAllowance(request.getDaysOfAllowance().get(), configuration);
		final Money receiptsReimbursement = calculateReceipts(request.getReceipts().get(), configuration);

		final Money totalAmount = carUsageReimbursement.add(dailyAllowanceReimbursement).add(receiptsReimbursement);

		return totalAmount;
	}

	private Money calculateDailyAllowance(final DaysOfAllowance daysOfAllowance, final ReimbursementConfigurationDTO configuration) {
		if (configuration.getDailyAllowanceRate().isPresent()) {
			final String days = String.valueOf(daysOfAllowance.getValue());
			return configuration.getDailyAllowanceRate().get().multiply(days);
		}
		return new Money("0");
	}

	private Money calculateCarUsage(final CarMileage carMileage, final ReimbursementConfigurationDTO configuration) {
		final Optional<Money> carMileageRate = configuration.getCarMileageRate();
		final Optional<Money> distanceLimit = configuration.getDistancePriceLimit();

		if (carMileageRate.isPresent()) {
			final String carUsageInKm = String.valueOf(carMileage.getValue());
			final Money result = carMileageRate.get().multiply(carUsageInKm);

			if (distanceLimit.isPresent() && result.getValue().compareTo(distanceLimit.get().getValue()) > REIMBURSEMENT_LIMIT) {
				throw new LimitsReachedException("Reimbursement claim value exceeds limits");
			}

			return result;
		}
		return new Money("0");
	}

	private Money calculateReceipts(final List<Receipt> requestedReceipts, final ReimbursementConfigurationDTO configuration) {
		Money amount = new Money("0");

		final List<Receipt> nonDuplicateRequestedReceipts = getRidOfDuplicatedReceiptTypesAndAddThem(requestedReceipts);

		if (configuration.getReceipts().isPresent()) {
			final List<ReceiptTypeReimbursementLimit> receiptTypeReimbursementLimits = configuration.getReceipts().get();

			for (ReceiptTypeReimbursementLimit configurationReceipt : receiptTypeReimbursementLimits) {
				for (Receipt receipt : nonDuplicateRequestedReceipts) {
					if (receipt.getReceiptType().equals(configurationReceipt.getReceiptType())) {
						final Money money = receipt.getPrice();
						isReceiptLimitReached(configurationReceipt, money);
						amount = amount.add(money);
					}
				}
			}

		}

		return amount;
	}

	private static List<Receipt> getRidOfDuplicatedReceiptTypesAndAddThem(final List<Receipt> requestedReceipts) {
		final List<Receipt> nonDuplicateRequestedReceipts = new ArrayList<>();
		requestedReceipts.forEach(receipt -> {
			boolean isDuplicate = false;

			// Check if the receipt type is already in the nonDuplicateRequestedReceipts list
			for (Receipt existingReceipt : nonDuplicateRequestedReceipts) {
				if (existingReceipt.getReceiptType().equals(receipt.getReceiptType())) {
					isDuplicate = true;
					break;
				}
			}

			if (!isDuplicate) {
				nonDuplicateRequestedReceipts.add(receipt);
			} else {
				// Find the duplicate receipt in the list
				for (Receipt existingReceipt : nonDuplicateRequestedReceipts) {
					if (existingReceipt.getReceiptType().equals(receipt.getReceiptType())) {
						Money total = receipt.getPrice().add(existingReceipt.getPrice());
						nonDuplicateRequestedReceipts.remove(existingReceipt);
						nonDuplicateRequestedReceipts.add(new Receipt(existingReceipt.getReceiptType(), total));
					}
				}
			}
		});

		return nonDuplicateRequestedReceipts;
	}

	private static void isReceiptLimitReached(final ReceiptTypeReimbursementLimit configurationReceipt, final Money money) {
		if (configurationReceipt.getReceiptLimit().isPresent()
		&& money.getValue().compareTo(configurationReceipt.getReceiptLimit().get().getValue()) > REIMBURSEMENT_LIMIT ) {
			throw new LimitsReachedException("Reimbursement claim value exceeds limits");
		}
	}

	private static void checkTotalReimbursementLimit(final ReimbursementConfigurationDTO configuration, final Money totalAmount) {
		final BigDecimal amount = totalAmount.getValue();
		if (configuration.getTotalReimbursementLimit().isPresent()) {
			final BigDecimal limit = configuration.getTotalReimbursementLimit().get().getValue();
			if (amount.compareTo(limit) > REIMBURSEMENT_LIMIT) {
				throw new LimitsReachedException("Reimbursement claim value exceeds limits");
			}
		}
	}

}
