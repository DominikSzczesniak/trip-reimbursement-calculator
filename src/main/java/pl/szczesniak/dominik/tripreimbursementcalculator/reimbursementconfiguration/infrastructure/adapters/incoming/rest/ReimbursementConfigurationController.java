package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ReimbursementConfigurationController implements HttpHandler {

	@Override
	public void handle(final HttpExchange exchange) throws IOException {

		System.out.println(exchange);

	}

}
