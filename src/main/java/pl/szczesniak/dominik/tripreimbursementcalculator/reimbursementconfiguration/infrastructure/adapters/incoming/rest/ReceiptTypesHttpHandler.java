package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;

import java.io.IOException;

public class ReceiptTypesHttpHandler implements HttpHandler {
	private final ReimbursementConfigurationController configurationController;

	public ReceiptTypesHttpHandler(final ReimbursementConfigurationService service) {
		this.configurationController = new ReimbursementConfigurationController(service);
	}

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:63342");
		exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
		if ("GET".equals(exchange.getRequestMethod())) {
			configurationController.getAvailableReceipts(exchange);
		} else {
			exchange.sendResponseHeaders(404, -1);
		}
	}
}
