package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;

import java.io.IOException;

public class ReimbursementConfigurationHttpHandler implements HttpHandler {

	private final ReimbursementConfigurationController configurationController;

	public ReimbursementConfigurationHttpHandler(final ReimbursementConfigurationService service) {
		this.configurationController = new ReimbursementConfigurationController(service);
	}

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		if ("PUT".equals(exchange.getRequestMethod())) {
			configurationController.setConfiguration(exchange);
		} else if ("GET".equals(exchange.getRequestMethod())) {
			configurationController.getConfiguration(exchange);
		} else {
			exchange.sendResponseHeaders(404, -1);
		}
	}

}
