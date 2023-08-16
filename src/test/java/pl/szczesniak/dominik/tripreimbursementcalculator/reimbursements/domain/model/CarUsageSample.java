package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

public class CarUsageSample {

	public static CarUsage createAnyCarUsage() {
		return new CarUsage(Math.random());
	}

}
