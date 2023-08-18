package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.util.Random;

public class DaysOfAllowanceSample {

	public static DaysOfAllowance createAnyDaysOfAllowance() {
		final Random random = new Random();
		return new DaysOfAllowance(random.nextInt(100));
	}

}
