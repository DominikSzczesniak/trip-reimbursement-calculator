package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;

import java.util.Objects;
import java.util.Optional;

public class ReceiptTypeReimbursementLimit {

	private final ReceiptType receiptType;

	private Money receiptLimit;

	public ReceiptTypeReimbursementLimit(final ReceiptType receiptType, final Money receiptLimit) {
		this.receiptType = receiptType;
		this.receiptLimit = receiptLimit;
	}

	public ReceiptTypeReimbursementLimit(final ReceiptType receiptType) {
		this.receiptType = receiptType;
	}

	public ReceiptType getReceiptType() {
		return receiptType;
	}

	public Optional<Money> getReceiptLimit() {
		return Optional.ofNullable(receiptLimit);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReceiptTypeReimbursementLimit that = (ReceiptTypeReimbursementLimit) o;
		return Objects.equals(receiptType, that.receiptType) && Objects.equals(receiptLimit, that.receiptLimit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(receiptType, receiptLimit);
	}
}
