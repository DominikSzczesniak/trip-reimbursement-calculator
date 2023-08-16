package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

public class DailyAllowanceRate {

	private final double value;

	public DailyAllowanceRate(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
