package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.util.Objects;

public class Receipt {

	private final int id;

	private final String receiptType;

	public Receipt(final int id, final String receiptType) {
		this.id = id;
		this.receiptType = receiptType;
	}

	public int getId() {
		return id;
	}

	public String getReceiptType() {
		return receiptType;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Receipt receipt = (Receipt) o;
		return id == receipt.id && Objects.equals(receiptType, receipt.receiptType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, receiptType);
	}
}
