package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;

import java.util.Objects;

public class ReimbursementRequestResult {

	private final ReimbursementId id;

	private final Money totalReimbursementAmount;


	public ReimbursementRequestResult(final ReimbursementId id, final Money totalReimbursementAmount) {
		this.id = id;
		this.totalReimbursementAmount = totalReimbursementAmount;
	}


	public Money getTotalReimbursementAmount() {
		return totalReimbursementAmount;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReimbursementRequestResult that = (ReimbursementRequestResult) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "FinalReimbursementDTO{" +
				"id=" + id +
				", totalReimbursement=" + totalReimbursementAmount +
				'}';
	}
}
