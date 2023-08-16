package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration;

public class DailyAllowanceRateDaily {

	private final double value;

	public DailyAllowanceRateDaily(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
