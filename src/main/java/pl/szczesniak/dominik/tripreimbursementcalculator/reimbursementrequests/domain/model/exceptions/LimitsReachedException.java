package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.exceptions;

public class LimitsReachedException extends RuntimeException {

	public LimitsReachedException(final String message) {
		super(message);
	}

}
