package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReceiptType;

import java.util.List;
import java.util.Optional;

public class ReimbursementConfigurationService {

	public ReimbursementConfigurationDTO getReimbursementConfiguration() {
		return null;
	}

	static class ReimbursementConfigurationDTO {

		private final Money carMileageRate;

		private final Money dailyAllowanceRate;

		private final List<ReceiptType> receipts;

		private final Money totalReimbursementLimit;

		private final Money distancePriceLimit;

		ReimbursementConfigurationDTO(Money carMileageRate, Money dailyAllowanceRate, List<ReceiptType> receipts, Money totalReimbursementLimit, Money distancePriceLimit) {
			this.carMileageRate = carMileageRate;
			this.dailyAllowanceRate = dailyAllowanceRate;
			this.receipts = receipts;
			this.totalReimbursementLimit = totalReimbursementLimit;
			this.distancePriceLimit = distancePriceLimit;
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

		Optional<Money> getTotalReimbursementLimit() {
			return Optional.ofNullable(totalReimbursementLimit);
		}

		Optional<List<ReceiptType>> getReceipts() {
			return Optional.ofNullable(receipts);
		}

		Optional<Money> getDistancePriceLimit() {
			return Optional.ofNullable(distancePriceLimit);
		}

		public static class ReimbursementConfigurationDTOBuilder {
			private Money carMileageRate;
			private Money dailyAllowanceRate;
			private List<ReceiptType> receipts;
			private Money totalReimbursementLimit;
			private Money distancePriceLimit;

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

			public ReimbursementConfigurationDTOBuilder receipts(List<ReceiptType> receipts) {
				this.receipts = receipts;
				return this;
			}

			public ReimbursementConfigurationDTOBuilder totalReimbursementLimit(Money totalReimbursementLimit) {
				this.totalReimbursementLimit = totalReimbursementLimit;
				return this;
			}

			public ReimbursementConfigurationDTOBuilder distancePriceLimit(Money distancePriceLimit) {
				this.distancePriceLimit = distancePriceLimit;
				return this;
			}

			public ReimbursementConfigurationDTO build() {
				return new ReimbursementConfigurationDTO(this.carMileageRate, this.dailyAllowanceRate, this.receipts, this.totalReimbursementLimit, this.distancePriceLimit);
			}

			public String toString() {
				return "ReimbursementConfigurationService.ReimbursementConfigurationDTO.ReimbursementConfigurationDTOBuilder(carMileageRate=" + this.carMileageRate + ", dailyAllowanceRate=" + this.dailyAllowanceRate + ", receipts=" + this.receipts + ", totalReimbursementLimit=" + this.totalReimbursementLimit + ", distancePriceLimit=" + this.distancePriceLimit + ")";
			}
		}
	}

}
