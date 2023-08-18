package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.util.Objects;

public class DaysOfAllowance {

	private final int value;

	public DaysOfAllowance(final int value) {
		if (value < 0) {
			throw new IllegalArgumentException("Value cannot be lower than 0");
		}
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final DaysOfAllowance that = (DaysOfAllowance) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "DaysOfAllowance{" +
				"value=" + value +
				'}';
	}

}
