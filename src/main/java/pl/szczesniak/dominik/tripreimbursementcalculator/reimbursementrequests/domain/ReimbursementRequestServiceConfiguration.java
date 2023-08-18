package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

public class ReimbursementRequestServiceConfiguration {

	public ReimbursementRequestService reimbursementRequestService(final ReimbursementConfigurationService reimbursementConfigurationService,
																   final ReimbursementRequestsRepository repository) {
		return new ReimbursementRequestService(reimbursementConfigurationService, repository);
	}

}
