package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

public class ReimbursementRequestServiceConfiguration {

	public ReimbursementRequestService reimbursementRequestService(final ReimbursementConfigurationService reimbursementConfigurationService) {
		return new ReimbursementRequestService(reimbursementConfigurationService);
	}

//	public ConfigurationProvider configurationProvider(ConfiguService) {
//		// TODO:
//		throw new RuntimeException("Not implemented yet");
//	}

}
