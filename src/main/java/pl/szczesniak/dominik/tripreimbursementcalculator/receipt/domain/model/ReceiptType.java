package pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model;

import java.util.Objects;

public class ReceiptType {

	private final String value;

	public ReceiptType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReceiptType that = (ReceiptType) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Receipt{" +
				"value='" + value + '\'' +
				'}';
	}

}
