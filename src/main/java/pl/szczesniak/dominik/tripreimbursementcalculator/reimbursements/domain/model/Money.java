package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

	private final BigDecimal amount;
	private final Currency currency;

	public Money(final BigDecimal amount, final Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money add(final Money other) {
		if (!currency.equals(other.currency)) {
			throw new IllegalArgumentException("Currencies must match for addition");
		}
		final BigDecimal newAmount = amount.add(other.amount);
		return new Money(newAmount, currency);
	}

	public Money subtract(final Money other) {
		if (!currency.equals(other.currency)) {
			throw new IllegalArgumentException("Currencies must match for subtraction");
		}
		final BigDecimal newAmount = amount.subtract(other.amount);
		return new Money(newAmount, currency);
	}

	public Money multiply(final BigDecimal multiplier) {
		final BigDecimal newAmount = amount.multiply(multiplier);
		return new Money(newAmount, currency);
	}

	@Override
	public String toString() {
		return "Money{" +
				"amount=" + amount +
				", currency=" + currency +
				'}';
	}

}
