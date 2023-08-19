package pl.szczesniak.dominik.tripreimbursementcalculator;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationServiceConfig;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestServiceConfiguration;

import java.io.IOException;

public class ReimbursementApp {

	public static void main(String[] args) throws IOException {

		final ReimbursementConfigurationService config = new ReimbursementConfigurationServiceConfig().reimbursementConfigurationService();
		final ReimbursementRequestService service = new ReimbursementRequestServiceConfiguration().reimbursementRequestService(config);
		AppInitializer initializer = new AppInitializer(config, service);
		initializer.initialize();

	}

}
