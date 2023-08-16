package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration;

public class CarUsageRatePerKilometer {

	private final double value;

	public CarUsageRatePerKilometer(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
