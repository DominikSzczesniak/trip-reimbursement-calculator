package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;

import java.util.Objects;

public class Receipt {

	private final ReceiptType receiptType;

	private final Money price;

	public Receipt(final ReceiptType receiptType, final Money price) {
		this.receiptType = receiptType;
		this.price = price;
	}

	public Money getPrice() {
		return price;
	}

	public ReceiptType getReceiptType() {
		return receiptType;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Receipt receipt = (Receipt) o;
		return Objects.equals(receiptType, receipt.receiptType) && Objects.equals(price, receipt.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(receiptType, price);
	}

	@Override
	public String toString() {
		return "Receipt{" +
				"value='" + price + '\'' +
				'}';
	}
}
