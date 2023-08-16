package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

public class CarUsageRate {

	private final double value;

	public CarUsageRate(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
