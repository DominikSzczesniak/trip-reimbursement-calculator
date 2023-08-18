package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.incoming.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationServiceConfig;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestServiceConfiguration;
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

public class ReimbursmentRequestsHttpHandler implements HttpHandler {


	private final ReimbursementConfigurationService config = new ReimbursementConfigurationServiceConfig().reimbursementConfigurationService();

	private final ReimbursementRequestService service = new ReimbursementRequestServiceConfiguration().reimbursementRequestService(config);

	@Override
	public void handle(final HttpExchange exchange) throws IOException {
		if ("POST".equals(exchange.getRequestMethod())) {
			handlePost(exchange);
		} else {
			exchange.sendResponseHeaders(404, -1);
		}
	}

	private void handlePost(final HttpExchange exchange) throws IOException {
		Map<String, Object> dto = deserialize(exchange.getRequestBody().readAllBytes());
		SubmitReimbursementRequest command = createCommand(dto);
		ReimbursementRequestResult result = service.submitReimbursementRequest(command);
		fillHttpExchangeWithRequestBody(exchange, result);

	}

	private Map<String, Object> deserialize(final byte[] bytes) throws JsonProcessingException {
		String jsonString = new String(bytes, StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> dto = objectMapper.readValue(jsonString, new TypeReference<>() {
		});
		return dto;
	}

	private SubmitReimbursementRequest createCommand(final Map<String, Object> dto) {
		String tripDate = (String) dto.get("tripDate");
		double carMileAge = (double) dto.get("carMileAge");
		int daysOfAllowance = (int) dto.get("daysOfAllowance");
		List<Map<String, Object>> receiptsData = (List<Map<String, Object>>) dto.get("receipts");
		List<ReceiptDTO> receiptsDto = new ArrayList<>();

		for (Map<String, Object> receiptData : receiptsData) {
			String receiptType = (String) receiptData.get("receiptType");
			double price = (double) receiptData.get("price");
			receiptsDto.add(new ReceiptDTO(receiptType, price));
		}

		ReimbursementRequestDTO requestDTO = new ReimbursementRequestDTO(tripDate, carMileAge, daysOfAllowance, receiptsDto);

		LocalDate tripDateParsed = LocalDate.parse(requestDTO.getTripDate());
		double carMileAge1 = requestDTO.getCarMileAge();
		List<Receipt> receipts = new ArrayList<>();
		receiptsDto.forEach(receiptDTO -> receipts.add(new Receipt(new ReceiptType(receiptDTO.getReceiptType()), new Money(String.valueOf(receiptDTO.getPrice())))));
		int daysOfAllowance1 = requestDTO.getDaysOfAllowance();

		return new SubmitReimbursementRequest(
				new TripDate(tripDateParsed),
				new CarMileage(carMileAge1),
				new DaysOfAllowance(daysOfAllowance1),
				receipts
		);
	}

	private void fillHttpExchangeWithRequestBody(final HttpExchange exchange, final ReimbursementRequestResult result) throws IOException {
		// Convert ReimbursementRequestResult to DTO
		ReimbursementRequestResultDTO resultDTO = new ReimbursementRequestResultDTO(
				result.getId().getValue().toString(),
				result.getTotalReimbursementAmount().getValue()
		);

		// Serialize DTO to JSON
		ObjectMapper objectMapper = new ObjectMapper();
		String resultJson = objectMapper.writeValueAsString(resultDTO);

		// Set response headers
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, resultJson.length());

		// Write the JSON data to the response body
		OutputStream responseBody = exchange.getResponseBody();
		responseBody.write(resultJson.getBytes());
		responseBody.close();
	}

	public static class ReimbursementRequestDTO {
		private final String tripDate;
		private final double carMileAge;
		private final int daysOfAllowance;
		private final List<ReceiptDTO> receipts;

		ReimbursementRequestDTO(final String tripDate, final double carMileAge, final int daysOfAllowance, final List<ReceiptDTO> receipts) {
			this.tripDate = tripDate;
			this.carMileAge = carMileAge;
			this.daysOfAllowance = daysOfAllowance;
			this.receipts = receipts;
		}

		String getTripDate() {
			return tripDate;
		}

		double getCarMileAge() {
			return carMileAge;
		}

		int getDaysOfAllowance() {
			return daysOfAllowance;
		}


	}

	public static class ReceiptDTO {

		private String receiptType;
		private double price;

		ReceiptDTO(final String receiptType, final double price) {
			this.receiptType = receiptType;
			this.price = price;
		}

		String getReceiptType() {
			return receiptType;
		}

		double getPrice() {
			return price;
		}

	}

}
