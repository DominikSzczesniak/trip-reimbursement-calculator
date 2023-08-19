package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.incoming.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptTypeReimbursementLimitDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReceiptTypeReimbursementLimit;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementConfigurationController {

	private static final String NO_LIMIT = "9999999999999999999999999999";

	private final ReimbursementConfigurationService service;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public ReimbursementConfigurationController(final ReimbursementConfigurationService service) {
		this.service = service;
	}

	public void setConfiguration(final HttpExchange exchange) throws IOException {
		try (InputStream requestBody = exchange.getRequestBody()) {
			final ReimbursementConfigurationDTO configurationDTO = objectMapper.readValue(requestBody, ReimbursementConfigurationDTO.class);
			final List<ReceiptTypeReimbursementLimit> receiptLimits = convertIncomingReceipts(configurationDTO);

			final ReimbursementConfiguration updatedConfiguration = new ReimbursementConfiguration(
					new Money(configurationDTO.getCarMileageRate()),
					new Money(configurationDTO.getDailyAllowanceRate()),
					receiptLimits,
					new Money(configurationDTO.getTotalReimbursementLimit()),
					new Money(configurationDTO.getDistancePriceLimit())
			);

			service.setReimbursementConfiguration(updatedConfiguration);
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, -1);
		}

	}

	void getConfiguration(final HttpExchange exchange) throws IOException {
		final ReimbursementConfiguration reimbursementConfiguration = service.getReimbursementConfiguration();
		final List<ReceiptTypeReimbursementLimitDTO> receiptLimits = convertReceiptListLimitToDTO(reimbursementConfiguration);

		final ReimbursementConfigurationDTO reimbursementConfigurationDTO = new ReimbursementConfigurationDTO(
				reimbursementConfiguration.getCarMileageRate().get().getValue(),
				reimbursementConfiguration.getDailyAllowanceRate().get().getValue(),
				reimbursementConfiguration.getTotalReimbursementLimit().orElse(new Money(NO_LIMIT)).getValue(),
				reimbursementConfiguration.getDistancePriceLimit().orElse(new Money(NO_LIMIT)).getValue(),
				receiptLimits);


		final String resultJson = objectMapper.writeValueAsString(reimbursementConfigurationDTO);
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, resultJson.length());

		try (OutputStream responseBody = exchange.getResponseBody()) {
			responseBody.write(resultJson.getBytes());
		}
	}

	void getAvailableReceipts(final HttpExchange exchange) throws IOException {
		final List<String> receiptTypes = getReceiptTypes();
		final String responseJson = objectMapper.writeValueAsString(receiptTypes);

		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, responseJson.length());

		try (OutputStream responseBody = exchange.getResponseBody()) {
			responseBody.write(responseJson.getBytes());
		}
	}

	private static List<ReceiptTypeReimbursementLimit> convertIncomingReceipts(final ReimbursementConfigurationDTO configurationDTO) {
		final List<ReceiptTypeReimbursementLimit> receiptLimits = new ArrayList<>();
		for (ReceiptTypeReimbursementLimitDTO receiptLimitDTO : configurationDTO.getReceipts()) {
			final ReceiptType receiptType = new ReceiptType(receiptLimitDTO.getReceiptType());
			final Money receiptLimit = new Money(receiptLimitDTO.getReceiptLimit().toPlainString());
			receiptLimits.add(new ReceiptTypeReimbursementLimit(receiptType, receiptLimit));
		}
		return receiptLimits;
	}

	private static List<ReceiptTypeReimbursementLimitDTO> convertReceiptListLimitToDTO(final ReimbursementConfiguration reimbursementConfiguration) {
		final List<ReceiptTypeReimbursementLimitDTO> receiptLimits = new ArrayList<>();
		for (ReceiptTypeReimbursementLimit receiptLimit : reimbursementConfiguration.getReceipts().orElse(new ArrayList<>())) {
			receiptLimits.add(new ReceiptTypeReimbursementLimitDTO(
					receiptLimit.getReceiptType().getValue(),
					new BigDecimal(receiptLimit.getReceiptLimit().orElse(new Money("0")).getValue())
			));
		}
		return receiptLimits;
	}

	private List<String> getReceiptTypes() {
		final List<ReceiptTypeReimbursementLimit> receiptTypeReimbursementLimits = service.getReimbursementConfiguration()
				.getReceipts()
				.orElse(new ArrayList<>());
		final List<ReceiptType> receiptTypes = new ArrayList<>();
		for (ReceiptTypeReimbursementLimit limit : receiptTypeReimbursementLimits) {
			receiptTypes.add(limit.getReceiptType());
		}

		final List<String> receiptTypeNames = new ArrayList<>();
		for (ReceiptType receiptType : receiptTypes) {
			receiptTypeNames.add(receiptType.getValue());
		}
		return receiptTypeNames;
	}

	private static class ReimbursementConfigurationDTO {

		private String carMileageRate;

		private String dailyAllowanceRate;

		private String totalReimbursementLimit;

		private String distancePriceLimit;

		private List<ReceiptTypeReimbursementLimitDTO> receipts;

		public ReimbursementConfigurationDTO(final String carMileageRate,
											 final String dailyAllowanceRate,
											 final String totalReimbursementLimit,
											 final String distancePriceLimit,
											 final List<ReceiptTypeReimbursementLimitDTO> receipts) {
			this.carMileageRate = carMileageRate;
			this.dailyAllowanceRate = dailyAllowanceRate;
			this.totalReimbursementLimit = totalReimbursementLimit;
			this.distancePriceLimit = distancePriceLimit;
			this.receipts = receipts;
		}

		public ReimbursementConfigurationDTO() {
		}

		public String getCarMileageRate() {
			return carMileageRate;
		}

		public String getDailyAllowanceRate() {
			return dailyAllowanceRate;
		}

		public String getTotalReimbursementLimit() {
			return totalReimbursementLimit;
		}

		public String getDistancePriceLimit() {
			return distancePriceLimit;
		}

		public List<ReceiptTypeReimbursementLimitDTO> getReceipts() {
			return receipts;
		}
	}

}
