package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;

public class ReimbursementConfigurationService {

	private final ReimbursementConfigurationRepository repository;

	ReimbursementConfigurationService(final ReimbursementConfigurationRepository repository) {
		this.repository = repository;
	}

	public void setReimbursementConfiguration(final ReimbursementConfiguration configuration) {
		repository.setConfiguration(configuration);
	}

	public ReimbursementConfiguration getReimbursementConfiguration() {
		return repository.getLatestConfiguration().orElseGet(() -> ReimbursementConfiguration.builder().build());
	}

}
