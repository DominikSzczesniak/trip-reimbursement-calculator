package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.time.LocalDate;

public class TripDateSample {

	public static TripDate createAnyTripDate() {
		return new TripDate(LocalDate.now());
	}

}
