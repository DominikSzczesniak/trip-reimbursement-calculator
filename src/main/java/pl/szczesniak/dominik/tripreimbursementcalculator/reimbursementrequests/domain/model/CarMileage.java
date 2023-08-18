package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class CarMileage {

	private final BigDecimal value;

	public CarMileage(final double value) {
		if (value < 0) {
			throw new IllegalArgumentException("Value cannot be lower than 0");
		}
		this.value = new BigDecimal(value);
	}

	public double getValue() {
		return value.doubleValue();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final CarMileage that = (CarMileage) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "CarUsage{" +
				"value=" + value +
				'}';
	}

}
