package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

public class ReimbursementRequestServiceConfiguration {

	public ReimbursementRequestService reimbursementRequestService(final ConfigurationProvider configurationProvider) {
		return new ReimbursementRequestService(configurationProvider);
	}

//	public ConfigurationProvider configurationProvider(ConfiguService) {
//		// TODO:
//		throw new RuntimeException("Not implemented yet");
//	}

}
