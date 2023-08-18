package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.outgoing.persistence.InMemoryReimbursementConfigurationRepository;

public class ReimbursementConfigurationServiceConfig {

	public  ReimbursementConfigurationService reimbursementConfigurationService() {
		return new ReimbursementConfigurationService(new InMemoryReimbursementConfigurationRepository());
	}

}
