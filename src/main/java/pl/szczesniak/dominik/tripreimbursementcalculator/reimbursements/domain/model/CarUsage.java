package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.util.Objects;

public class CarUsage {

	private final double value;

	public CarUsage(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final CarUsage carUsage = (CarUsage) o;
		return Double.compare(carUsage.value, value) == 0;
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
