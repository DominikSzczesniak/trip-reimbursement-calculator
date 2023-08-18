package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

public class CarUsageSample {

	public static CarMileage createAnyCarUsage() {
		return new CarMileage(Math.random());
	}

}
