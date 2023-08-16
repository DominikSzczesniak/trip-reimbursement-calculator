package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

public class ReimbursementFacade {

	private final ReimbursementRequestService reimbursementRequestService;

	public ReimbursementFacade(final ReimbursementRequestService reimbursementRequestService) {
		this.reimbursementRequestService = reimbursementRequestService;
	}



}
