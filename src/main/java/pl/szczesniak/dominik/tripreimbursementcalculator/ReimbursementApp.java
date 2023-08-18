package pl.szczesniak.dominik.tripreimbursementcalculator;

import com.sun.net.httpserver.HttpServer;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest.ReimbursementConfigurationController;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.incoming.rest.ReimbursmentRequestsHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ReimbursementApp {

	public static void main(String[] args) throws IOException {

		int port = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		server.createContext("/api/trip-reimbursement-requests", new ReimbursmentRequestsHttpHandler());
		server.createContext("/api/trip-reimbursement-requests/configuration", new ReimbursementConfigurationController());

		server.setExecutor(null);
		server.start();

		System.out.println("Server started on port " + port);
	}

}
