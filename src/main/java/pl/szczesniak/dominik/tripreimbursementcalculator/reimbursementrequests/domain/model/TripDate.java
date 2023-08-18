package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class TripDate {

	private final LocalDate value;

	public TripDate(final LocalDate value) {
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null");
		}
		this.value = value;
	}

	public LocalDate getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "TripDate{" +
				"localDate=" + value +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TripDate tripDate = (TripDate) o;
		return Objects.equals(value, tripDate.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
