package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReimbursementConfiguration {

	private static final String MINIMAL_CAR_MILEAGE_RATE = "0.3";

	public static final String MINIMAL_DAILY_ALLOWANCE_RATE = "15";

	private Money carMileageRate = new Money(MINIMAL_CAR_MILEAGE_RATE);

	private Money dailyAllowanceRate = new Money(MINIMAL_DAILY_ALLOWANCE_RATE);

	private final List<ReceiptTypeReimbursementLimit> receipts;

	private final Money totalReimbursementLimit;

	private final Money distancePriceLimit;

	public ReimbursementConfiguration(final Money carMileageRate,
									  final Money dailyAllowanceRate,
									  final List<ReceiptTypeReimbursementLimit> receipts,
									  final Money totalReimbursementLimit,
									  final Money distancePriceLimit) {
		if (carMileageRate != null) {
			this.carMileageRate = carMileageRate;
		}
		if (dailyAllowanceRate != null) {
			this.dailyAllowanceRate = dailyAllowanceRate;
		}
		this.receipts = receipts;
		this.totalReimbursementLimit = totalReimbursementLimit;
		this.distancePriceLimit = distancePriceLimit;
	}

	public static ReimbursementConfigurationDTOBuilder builder() {
		return new ReimbursementConfigurationDTOBuilder();
	}

	public Optional<Money> getCarMileageRate() {
		return Optional.ofNullable(carMileageRate);
	}

	public Optional<Money> getDailyAllowanceRate() {
		return Optional.ofNullable(dailyAllowanceRate);
	}

	public Optional<Money> getTotalReimbursementLimit() {
		return Optional.ofNullable(totalReimbursementLimit);
	}

	public Optional<List<ReceiptTypeReimbursementLimit>> getReceipts() {
		return Optional.ofNullable(receipts);
	}

	public Optional<Money> getDistancePriceLimit() {
		return Optional.ofNullable(distancePriceLimit);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReimbursementConfiguration that = (ReimbursementConfiguration) o;
		return Objects.equals(carMileageRate, that.carMileageRate) && Objects.equals(dailyAllowanceRate, that.dailyAllowanceRate) && Objects.equals(receipts, that.receipts) && Objects.equals(totalReimbursementLimit, that.totalReimbursementLimit) && Objects.equals(distancePriceLimit, that.distancePriceLimit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(carMileageRate, dailyAllowanceRate, receipts, totalReimbursementLimit, distancePriceLimit);
	}

	@Override
	public String toString() {
		return "ReimbursementConfiguration{" +
				"carMileageRate=" + carMileageRate +
				", dailyAllowanceRate=" + dailyAllowanceRate +
				", receipts=" + receipts +
				", totalReimbursementLimit=" + totalReimbursementLimit +
				", distancePriceLimit=" + distancePriceLimit +
				'}';
	}

	public static class ReimbursementConfigurationDTOBuilder {
		private Money carMileageRate;
		private Money dailyAllowanceRate;
		private List<ReceiptTypeReimbursementLimit> receipts;
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

		public ReimbursementConfigurationDTOBuilder receipts(List<ReceiptTypeReimbursementLimit> receipts) {
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

		public ReimbursementConfiguration build() {
			return new ReimbursementConfiguration(this.carMileageRate, this.dailyAllowanceRate, this.receipts, this.totalReimbursementLimit, this.distancePriceLimit);
		}


		@Override
		public String toString() {
			return "ReimbursementConfigurationDTOBuilder{" +
					"carMileageRate=" + carMileageRate +
					", dailyAllowanceRate=" + dailyAllowanceRate +
					", receipts=" + receipts +
					", totalReimbursementLimit=" + totalReimbursementLimit +
					", distancePriceLimit=" + distancePriceLimit +
					'}';
		}

	}

}
