package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.time.LocalDate;

public class TripDateSample {

	public static TripDate createAnyTripDate() {
		return new TripDate(LocalDate.now());
	}

}
