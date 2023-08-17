package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementConfigurationService.ReimbursementConfigurationDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;

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
				command.getDaysOfAllowance().orElse(new DaysOfAllowance(0))
		);

		final ReimbursementConfigurationDTO reimbursementConfiguration = reimbursementConfigurationService.getReimbursementConfiguration();
		final Money totalReimbursementAmount = calculateTotalReimbursementAmount(reimbursement, reimbursementConfiguration);

		return new ReimbursementRequestResult(reimbursement.getReimbursementId(), totalReimbursementAmount);
	}

	private Money calculateTotalReimbursementAmount(final ReimbursementRequest request, final ReimbursementConfigurationDTO configuration) {
		final Money carUsageReimbursement = calculateCarUsage(
				request.getCarMileage().get(),
				configuration.getCarMileageRate().orElse(new Money("0.3")));
		final Money dailyAllowanceReimbursement = calculateDailyAllowance(
				request.getDaysOfAllowance().get(),
				configuration.getDailyAllowanceRate().orElse(new Money("15")));

		return carUsageReimbursement.add(dailyAllowanceReimbursement);
	}

	private Money calculateDailyAllowance(final DaysOfAllowance daysOfAllowance, final Money dailyAllowanceRate) {
		if (daysOfAllowance != null) {
			final String days = String.valueOf(daysOfAllowance.getValue());
			return dailyAllowanceRate.multiply(days);
		}
		return new Money("0");
	}

	private Money calculateCarUsage(final CarMileage carMileage, final Money carMileageRatePerKilometer) {
		if (carMileage != null) {
			final String carUsageInKm = String.valueOf(carMileage.getValue());
			return carMileageRatePerKilometer.multiply(carUsageInKm);
		}
		return new Money("0");
	}
}
