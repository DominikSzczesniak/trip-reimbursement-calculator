package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;

public class ReimbursementRequestServiceConfiguration {

	public ReimbursementRequestService reimbursementRequestService(final ReimbursementConfigurationService reimbursementConfigurationService,
																   final ReimbursementRequestsRepository repository) {
		return new ReimbursementRequestService(reimbursementConfigurationService, repository);
	}

}
