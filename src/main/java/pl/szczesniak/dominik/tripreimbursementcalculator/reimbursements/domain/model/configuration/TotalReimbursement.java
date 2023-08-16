package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration;

import java.util.Objects;

public class TotalReimbursement {

	private final double value;

	public TotalReimbursement(final double value) {
		this.value = value;
	}

	double getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TotalReimbursement that = (TotalReimbursement) o;
		return Double.compare(that.value, value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "TotalReimbursement{" +
				"value=" + value +
				'}';
	}

}
