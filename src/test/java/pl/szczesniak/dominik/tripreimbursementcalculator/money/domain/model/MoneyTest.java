package pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

	@Test
	void should_add() {
		// given
		final Money firstAmount = new Money("12.42");
		final Money secondAmount = new Money("6.44");

		// when
		final Money result = firstAmount.add(secondAmount);

		// then
		assertThat(result).isEqualTo(new Money("18.86"));
	}

	@Test
	void should_multiply() {
		// given
		final Money amount = new Money("2.40");

		// when
		final Money result = amount.multiply("3");

		// then
		assertThat(result).isEqualTo(new Money("7.20"));
	}

	@Test
	void should_compare() {
		// given
		final Money amount = new Money("2.40");
		final Money sameAmount = new Money("2.40");
		final Money lowerAmount = new Money("2.00");
		final Money higherAmount = new Money("3.40");

		// when
		int equal = amount.compareTo(sameAmount);
		int higher = amount.compareTo(lowerAmount);
		int lower = amount.compareTo(higherAmount);

		// then
		assertThat(equal).isEqualTo(0);
		assertThat(higher).isEqualTo(1);
		assertThat(lower).isEqualTo(-1);
	}
}