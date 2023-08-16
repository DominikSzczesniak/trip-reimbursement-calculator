package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MoneyTest {

	@Test
	void should_add() {
		// given
		final Currency usd = Currency.getInstance("USD");
		final Money firstAmount = new Money(new BigDecimal("12.42"), usd);
		final Money secondAmount = new Money(new BigDecimal("6.44"), usd);

		// when
		final Money result = firstAmount.add(secondAmount);

		// then
		assertThat(result.getAmount()).isEqualTo("18.86");
		assertThat(result.getCurrency()).isEqualTo(usd);
	}

	@Test
	void should_substract() {
		// given
		final Currency usd = Currency.getInstance("USD");
		final Money firstAmount = new Money(new BigDecimal("12.42"), usd);
		final Money secondAmount = new Money(new BigDecimal("6.44"), usd);

		// when
		final Money result = firstAmount.subtract(secondAmount);

		// then
		assertThat(result.getAmount()).isEqualTo("5.98");
		assertThat(result.getCurrency()).isEqualTo(usd);
	}

	@Test
	void should_multiply() {
		// given
		final Currency usd = Currency.getInstance("USD");
		final Money amount = new Money(new BigDecimal("2.40"), usd);

		// when
		final Money result = amount.multiply(new BigDecimal("3"));

		// then
		assertThat(result.getAmount()).isEqualTo("7.20");
		assertThat(result.getCurrency()).isEqualTo(usd);
	}

	@Test
	void should_not_be_able_to_operate_on_different_currencies() {
		// given
		final Currency usd = Currency.getInstance("USD");
		final Currency eur = Currency.getInstance("EUR");
		final Money usDollar = new Money(new BigDecimal("12.42"), usd);
		final Money euro = new Money(new BigDecimal("6.44"), eur);

		// when
		final Throwable thrownOne = catchThrowable(() -> usDollar.add(euro));
		final Throwable thrownTwo = catchThrowable(() -> usDollar.subtract(euro));

		// then
		assertThat(thrownOne).isInstanceOf(IllegalArgumentException.class);
		assertThat(thrownTwo).isInstanceOf(IllegalArgumentException.class);
	}

}