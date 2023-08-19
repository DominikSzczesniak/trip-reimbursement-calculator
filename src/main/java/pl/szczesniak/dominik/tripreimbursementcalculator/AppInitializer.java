package pl.szczesniak.dominik.tripreimbursementcalculator;

import com.sun.net.httpserver.HttpServer;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest.ReceiptTypesHttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest.ReimbursementConfigurationHttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.incoming.rest.ReimbursmentRequestsHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AppInitializer {

	private final ReimbursementRequestService service;
	private final ReimbursementConfigurationService config;

	AppInitializer(final ReimbursementConfigurationService config, final ReimbursementRequestService service) {
		this.service = service;
		this.config = config;
	}

	public void initialize() throws IOException {
		int port = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		final ReimbursmentRequestsHttpHandler requestsHttpHandler = new ReimbursmentRequestsHttpHandler(service);
		server.createContext("/api/trip-reimbursement-requests", requestsHttpHandler);

		final ReimbursementConfigurationHttpHandler configurationHttpHandler = new ReimbursementConfigurationHttpHandler(config);
		server.createContext("/api/trip-reimbursement-requests/configuration", configurationHttpHandler);

		final ReceiptTypesHttpHandler receiptTypesHttpHandler = new ReceiptTypesHttpHandler(config);
		server.createContext("/api/trip-reimbursements/configuration/receipt-types", receiptTypesHttpHandler);



		server.setExecutor(null);
		server.start();

		System.out.println("Server started on port " + port);
	}

}
