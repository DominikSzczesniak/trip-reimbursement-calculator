package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class TripDate {

	private final LocalDate localDate;

	public TripDate(final LocalDate localDate) {
		this.localDate = localDate;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	@Override
	public String toString() {
		return "TripDate{" +
				"localDate=" + localDate +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TripDate tripDate = (TripDate) o;
		return Objects.equals(localDate, tripDate.localDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(localDate);
	}
}
