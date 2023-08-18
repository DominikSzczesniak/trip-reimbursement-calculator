package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.ReimbursementConfigurationService;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReceiptTypeReimbursementLimit;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequestSample;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.exceptions.LimitsReachedException;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.infrastructure.adapters.outgoing.persistence.InMemoryReimbursementRequestsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReimbursementRequestServiceTest {

	private ReimbursementRequestService tut;

	private ReimbursementConfigurationService configurationService;

	@BeforeEach
	void setUp() {
		configurationService = mock(ReimbursementConfigurationService.class);
		tut = new ReimbursementRequestServiceConfiguration().reimbursementRequestService(configurationService, new InMemoryReimbursementRequestsRepository());
	}

	@Test
	void should_submit_car_mileage_based_on_configuration() {
		// given
		final Money carMileageRate = new Money("15.21");
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.carMileageRate(carMileageRate)
				.build());

		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(11))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("167.31"));
	}

	@Test
	void should_submit_daily_allowance_based_on_configuration() {
		// given
		final Money dailyAllowanceRate = new Money("1");
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.dailyAllowanceRate(dailyAllowanceRate)
				.build());

		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.daysOfAllowance(new DaysOfAllowance(4))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("4.00"));
	}

	@Test
	void should_submit_receipts_based_on_configuration() {
		// given
		final List<ReceiptTypeReimbursementLimit> receipts = List.of(
				new ReceiptTypeReimbursementLimit(new ReceiptType("Plane")),
				new ReceiptTypeReimbursementLimit(new ReceiptType("Train")),
				new ReceiptTypeReimbursementLimit(new ReceiptType("Taxi"))
		);
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.receipts(receipts)
				.build());

		final List<Receipt> requestReceipts = new ArrayList<>();
		requestReceipts.add(new Receipt(new ReceiptType("Plane"), new Money("100")));
		requestReceipts.add(new Receipt(new ReceiptType("Taxi"), new Money("12")));
		requestReceipts.add(new Receipt(new ReceiptType("Taxi"), new Money("13")));
		requestReceipts.add(new Receipt(new ReceiptType("Taxi"), new Money("22")));
		requestReceipts.add(new Receipt(new ReceiptType("Train"), new Money("7.50")));
		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.receipts(requestReceipts)
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("154.50"));
	}

	@Test
	void should_not_add_reimbursement_when_invalid_receipt_type() {
		// given
		final List<ReceiptTypeReimbursementLimit> receipts = List.of(
				new ReceiptTypeReimbursementLimit(new ReceiptType("Plane")),
				new ReceiptTypeReimbursementLimit(new ReceiptType("Train")),
				new ReceiptTypeReimbursementLimit(new ReceiptType("Taxi"))
		);
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.receipts(receipts)
				.build());

		final List<Receipt> requestReceipts = new ArrayList<>();
		requestReceipts.add(new Receipt(new ReceiptType("Metro"), new Money("7.50")));
		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.receipts(requestReceipts)
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("0.00"));
	}

	@Test
	void should_submit_trip_reimbursement_request_calculating_every_reimbursement_base() {
		// given
		final Money carMileageRate = new Money("10");
		final Money dailyAllowanceRate = new Money("1");
		final List<ReceiptTypeReimbursementLimit> receipts = List.of(new ReceiptTypeReimbursementLimit(new ReceiptType("Uber")), new ReceiptTypeReimbursementLimit(new ReceiptType("Horse")));
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.carMileageRate(carMileageRate)
				.dailyAllowanceRate(dailyAllowanceRate)
				.receipts(receipts)
				.build());

		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(15))
				.daysOfAllowance(new DaysOfAllowance(4))
				.receipts(List.of(new Receipt(new ReceiptType("Uber"), new Money("24"))))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("178.00"));
		assertThat(result.getId()).isEqualTo(request.getId());
	}

	@Test
	void should_not_submit_request_when_total_reimbursement_limit_is_reached() {
		// given
		final Money carMileageRate = new Money("10");
		final Money dailyAllowanceRate = new Money("1");
		final Money totalReimbursementLimit = new Money("100");
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.carMileageRate(carMileageRate)
				.dailyAllowanceRate(dailyAllowanceRate)
				.totalReimbursementLimit(totalReimbursementLimit)
				.build());

		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(15))
				.daysOfAllowance(new DaysOfAllowance(4))
				.build();

		// when
		final Throwable thrown = catchThrowable(() -> tut.submitReimbursementRequest(request));

		// then
		assertThat(thrown).isInstanceOf(LimitsReachedException.class);
	}

	@Test
	void should_not_submit_request_when_distance_limit_is_reached() {
		// given
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.distancePriceLimit(new Money("10"))
				.carMileageRate(new Money("10"))
				.build());

		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(15))
				.build();

		// when
		final Throwable thrown = catchThrowable(() -> tut.submitReimbursementRequest(request));

		// then
		assertThat(thrown).isInstanceOf(LimitsReachedException.class);
	}

	@Test
	void should_not_submit_request_when_receipt_type_price_limit_is_reached() {
		// given
		final List<ReceiptTypeReimbursementLimit> receipts = List.of(
				new ReceiptTypeReimbursementLimit(new ReceiptType("Uber"), new Money("20")),
				new ReceiptTypeReimbursementLimit(new ReceiptType("Taxi"), new Money("10"))
		);
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.receipts(receipts)
				.build());

		final SubmitReimbursementRequest duplicatedReceiptTypeRequest = SubmitReimbursementRequestSample.builder()
				.receipts(List.of(
						new Receipt(new ReceiptType("Uber"), new Money("20")),
						new Receipt(new ReceiptType("Uber"), new Money("20")),
						new Receipt(new ReceiptType("Taxi"), new Money("20")))
				)
				.build();

		final SubmitReimbursementRequest notDuplicatedReceiptTyperequest = SubmitReimbursementRequestSample.builder()
				.receipts(List.of(
						new Receipt(new ReceiptType("Taxi"), new Money("20")))
				)
				.build();

		// when
		final Throwable thrownWithDuplicatedReceipts = catchThrowable(() -> tut.submitReimbursementRequest(duplicatedReceiptTypeRequest));
		final Throwable thrownWithoutDuplicatedReceipts = catchThrowable(() -> tut.submitReimbursementRequest(notDuplicatedReceiptTyperequest));

		// then
		assertThat(thrownWithDuplicatedReceipts).isInstanceOf(LimitsReachedException.class);
		assertThat(thrownWithoutDuplicatedReceipts).isInstanceOf(LimitsReachedException.class);
	}

	@Test
	void should_save_reimbursement_request() {
		// given
		when(configurationService.getReimbursementConfiguration()).thenReturn(ReimbursementConfiguration.builder()
				.build());
		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(15))
				.daysOfAllowance(new DaysOfAllowance(4))
				.receipts(List.of(new Receipt(new ReceiptType("Uber"), new Money("24"))))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		final ReimbursementRequest reimbursementRequest = tut.findReimbursementRequest(result.getId());
		assertThat(reimbursementRequest.getReimbursementId()).isEqualTo(result.getId());
	}
}