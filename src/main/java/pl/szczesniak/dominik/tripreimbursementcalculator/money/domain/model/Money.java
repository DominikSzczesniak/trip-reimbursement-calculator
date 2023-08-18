package pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {

	private final BigDecimal value;

	public Money(final String value) {
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null");
		}
		this.value = new BigDecimal(value);
	}

	public BigDecimal getValue() {
		return value;
	}

	public Money add(final Money other) {
		final String newAmount = value.add(other.value).toPlainString();
		return new Money(newAmount);
	}

	public Money multiply(final String multiplier) {
		final BigDecimal result = value.multiply(new BigDecimal(multiplier)).setScale(2, RoundingMode.CEILING);
		final String newAmount = String.valueOf(result);
		return new Money(newAmount);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Money money = (Money) o;
		return Objects.equals(value, money.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "Money{" +
				"amount=" + value +
				'}';
	}

}
