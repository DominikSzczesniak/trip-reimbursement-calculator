package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.incoming.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.exceptions.LimitsReachedException;

import java.io.IOException;
import java.io.OutputStream;

public class ReimbursmentRequestsHttpHandler implements HttpHandler {


	private final ReimbursementRequestsController reimbursementController;

	public ReimbursmentRequestsHttpHandler(final ReimbursementRequestService service) {
		this.reimbursementController = new ReimbursementRequestsController(service);
	}

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		if ("POST".equals(exchange.getRequestMethod())) {
			try {
				reimbursementController.submitRequest(exchange);
			} catch (LimitsReachedException e) {
				sendErrorResponse(exchange, 400, "LimitsReachedException: " + e.getMessage());
			}
		} else {
			exchange.sendResponseHeaders(404, -1);
		}
	}

	public void sendErrorResponse(final HttpExchange exchange, final int responseCode, final String errorMessage) throws IOException {
		exchange.getResponseHeaders().set("Content-Type", "text/plain");
		exchange.sendResponseHeaders(responseCode, errorMessage.getBytes().length);
		try (OutputStream outputStream = exchange.getResponseBody()) {
			outputStream.write(errorMessage.getBytes());
		}
	}
}
