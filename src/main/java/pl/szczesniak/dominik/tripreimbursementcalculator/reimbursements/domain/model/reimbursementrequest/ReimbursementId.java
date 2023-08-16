package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.reimbursementrequest;

import java.util.Objects;
import java.util.UUID;

public class ReimbursementId {

	private final UUID value;

	public ReimbursementId(final UUID value) {
		this.value = value;
	}

	public UUID getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReimbursementId id = (ReimbursementId) o;
		return Objects.equals(value, id.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "ReimbursementId{" +
				"value=" + value +
				'}';
	}

}
