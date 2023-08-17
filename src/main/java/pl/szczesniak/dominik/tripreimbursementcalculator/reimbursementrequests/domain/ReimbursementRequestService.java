package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ConfigurationProvider.ReimbursementConfigurationDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;

import java.util.UUID;

public class ReimbursementRequestService {

	private final ConfigurationProvider configurationProvider;

	ReimbursementRequestService(final ConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	public ReimbursementRequestResult submitReimbursementRequest(final SubmitReimbursementRequest command) {
		final ReimbursementRequest reimbursement = new ReimbursementRequest(
				new ReimbursementId(UUID.randomUUID()),
				command.getTripDate(),
				command.getCarUsage(),
				command.getTimeRange()
		);

		final ReimbursementConfigurationDTO reimbursementConfiguration = configurationProvider.getReimbursementConfiguration();
		final Money totalReimbursementAmount = calculateTotalReimbursementAmount(reimbursement, reimbursementConfiguration);

		return new ReimbursementRequestResult(reimbursement.getReimbursementId(), totalReimbursementAmount);
	}

	private Money calculateTotalReimbursementAmount(final ReimbursementRequest request, final ReimbursementConfigurationDTO configuration) {
		final Money carUsageReimbursement = calculateCarUsage(request.getCarUsage(), configuration.getCarMileageRate());
		final Money dailyAllowanceReimbursement = calculateDailyAllowance(request.getTimeRange(), configuration.getDailyAllowanceRate());
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
