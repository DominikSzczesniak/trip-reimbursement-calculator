package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.reimbursementrequest.TripDate;

import java.time.LocalDate;

public class TripDateSample {

	public static TripDate createAnyTripDate() {
		return new TripDate(LocalDate.now());
	}

}
