package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;

import java.util.Optional;

public interface ReimbursementConfigurationRepository {

	void setConfiguration(ReimbursementConfiguration reimbursementConfiguration);

	Optional<ReimbursementConfiguration> getLatestConfiguration();

}
