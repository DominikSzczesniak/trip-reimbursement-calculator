package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.incoming.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResultDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReimbursementRequestsController {

	private final ReimbursementRequestService service;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public ReimbursementRequestsController(final ReimbursementRequestService service) {
		this.service = service;
	}

	public void submitRequest(final HttpExchange exchange) throws IOException {
		final Map<String, Object> dto = deserialize(exchange.getRequestBody().readAllBytes());
		final SubmitReimbursementRequest command = createCommand(dto);
		final ReimbursementRequestResult result = service.submitReimbursementRequest(command);
		fillHttpExchangeWithRequestBody(exchange, result);
	}

	private Map<String, Object> deserialize(final byte[] bytes) throws JsonProcessingException {
		return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), Map.class);
	}

	private SubmitReimbursementRequest createCommand(final Map<String, Object> dto) {
		final String tripDate = (String) dto.get("tripDate");
		final Double carMileage = Optional.ofNullable((Double) dto.get("carMileage")).orElse(0.0);
		final Integer daysOfAllowance = Optional.ofNullable((Integer) dto.get("daysOfAllowance")).orElse(0);

		final List<Map<String, Object>> receiptsData = (List<Map<String, Object>>) dto.get("receipts");
		List<Receipt> receipts = new ArrayList<>();
		if (receiptsData != null) {
			receipts = receiptsData.stream()
					.map(receiptData -> new Receipt(
							new ReceiptType((String) receiptData.get("receiptType")),
							new Money(String.valueOf(receiptData.get("price"))))
					)
					.collect(Collectors.toList());
		}

		return new SubmitReimbursementRequest(
				new TripDate(LocalDate.parse(tripDate)),
				new CarMileage(carMileage),
				new DaysOfAllowance(daysOfAllowance),
				receipts
		);
	}

	private void fillHttpExchangeWithRequestBody(final HttpExchange exchange, final ReimbursementRequestResult result) throws IOException {
		final ReimbursementRequestResultDTO resultDTO = new ReimbursementRequestResultDTO(
				result.getId().getValue().toString(),
				new BigDecimal(result.getTotalReimbursementAmount().getValue())
		);

		final String resultJson = objectMapper.writeValueAsString(resultDTO);

		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(201, resultJson.length());

		try (OutputStream responseBody = exchange.getResponseBody()) {
			responseBody.write(resultJson.getBytes());
		}
	}

}
