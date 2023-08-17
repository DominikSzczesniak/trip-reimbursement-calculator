package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;

import java.util.Optional;

public class ReimbursementConfigurationService {

	public ReimbursementConfigurationDTO getReimbursementConfiguration() {
		return null;
	}

	static class ReimbursementConfigurationDTO {

		private final Money carMileageRate;

		private final Money dailyAllowanceRate;

		ReimbursementConfigurationDTO(final Money carMileageRate, final Money dailyAllowanceRate) {
			this.carMileageRate = carMileageRate;
			this.dailyAllowanceRate = dailyAllowanceRate;
		}

		public static ReimbursementConfigurationDTOBuilder builder() {
			return new ReimbursementConfigurationDTOBuilder();
		}

		Optional<Money> getCarMileageRate() {
			return Optional.ofNullable(carMileageRate);
		}

		Optional<Money> getDailyAllowanceRate() {
			return Optional.ofNullable(dailyAllowanceRate);
		}

		public static class ReimbursementConfigurationDTOBuilder {
			private Money carMileageRate;
			private Money dailyAllowanceRate;

			ReimbursementConfigurationDTOBuilder() {
			}

			public ReimbursementConfigurationDTOBuilder carMileageRate(Money carMileageRate) {
				this.carMileageRate = carMileageRate;
				return this;
			}

			public ReimbursementConfigurationDTOBuilder dailyAllowanceRate(Money dailyAllowanceRate) {
				this.dailyAllowanceRate = dailyAllowanceRate;
				return this;
			}

			public ReimbursementConfigurationDTO build() {
				return new ReimbursementConfigurationDTO(this.carMileageRate, this.dailyAllowanceRate);
			}

			@Override
			public String toString() {
				return "ReimbursementConfigurationDTOBuilder{" +
						"carMileageRate=" + carMileageRate +
						", dailyAllowanceRate=" + dailyAllowanceRate +
						'}';
			}
			
		}
	}

}
