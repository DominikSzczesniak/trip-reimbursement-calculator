package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class TimeRange {

	private final LocalDate start;

	private final LocalDate end;

	public TimeRange(final LocalDate start, final LocalDate end) {
		this.start = start;
		this.end = end;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public long countDays() {
		return ChronoUnit.DAYS.between(start, end);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TimeRange timeRange = (TimeRange) o;
		return Objects.equals(start, timeRange.start) && Objects.equals(end, timeRange.end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}

	@Override
	public String toString() {
		return "TimeRange{" +
				"start=" + start +
				", end=" + end +
				'}';
	}
}
