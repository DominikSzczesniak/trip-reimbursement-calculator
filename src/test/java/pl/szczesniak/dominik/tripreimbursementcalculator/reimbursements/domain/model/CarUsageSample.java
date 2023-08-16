package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.reimbursementrequest.CarUsage;

public class CarUsageSample {

	public static CarUsage createAnyCarUsage() {
		return new CarUsage(Math.random());
	}

}
