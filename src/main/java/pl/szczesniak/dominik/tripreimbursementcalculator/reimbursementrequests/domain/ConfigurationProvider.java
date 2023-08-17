package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;

public interface ConfigurationProvider {

	ReimbursementConfigurationDTO getReimbursementConfiguration();

	class ReimbursementConfigurationDTO {

		private final Money carMileageRate;

		private final Money dailyAllowanceRate;

		ReimbursementConfigurationDTO(final Money carMileageRate, final Money dailyAllowanceRate) {
			this.carMileageRate = carMileageRate;
			this.dailyAllowanceRate = dailyAllowanceRate;
		}

		Money getCarMileageRate() {
			return carMileageRate;
		}

		Money getDailyAllowanceRate() {
			return dailyAllowanceRate;
		}
	}

}
