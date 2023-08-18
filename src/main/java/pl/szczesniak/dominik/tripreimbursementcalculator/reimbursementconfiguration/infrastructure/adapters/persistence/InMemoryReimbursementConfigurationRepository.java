package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.persistence;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationRepository;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;

import java.util.Optional;

public class InMemoryReimbursementConfigurationRepository implements ReimbursementConfigurationRepository {

	private ReimbursementConfiguration configuration;

	@Override
	public void setConfiguration(final ReimbursementConfiguration reimbursementConfiguration) {
		configuration = reimbursementConfiguration;
	}

	@Override
	public Optional<ReimbursementConfiguration> getLatestConfiguration() {
		return Optional.ofNullable(configuration);
	}

}
