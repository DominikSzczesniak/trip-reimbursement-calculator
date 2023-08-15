package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

public class DailyAllowanceRate {

	private final double allowanceRate;

	public DailyAllowanceRate(final double allowanceRate) {
		this.allowanceRate = allowanceRate;
	}

	public double getAllowanceRate() {
		return allowanceRate;
	}

}
