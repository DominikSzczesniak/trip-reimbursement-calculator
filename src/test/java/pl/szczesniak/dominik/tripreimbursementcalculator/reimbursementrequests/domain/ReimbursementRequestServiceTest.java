package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ConfigurationProvider.ReimbursementConfigurationDTO;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementRequestResult;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands.SubmitReimbursementRequestSample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReimbursementRequestServiceTest {

	private ReimbursementRequestService tut;

	private ConfigurationProvider provider;

	@BeforeEach
	void setUp() {
		provider = mock(ConfigurationProvider.class);
		tut = new ReimbursementRequestServiceConfiguration().reimbursementRequestService(provider);
	}

	@Test
	void should_submit_car_mileage_based_on_configuration() {
		// given
		final Money carMileageRate = new Money("15.21");
		final Money dailyAllowanceRate = new Money("1.21");
		when(provider.getReimbursementConfiguration()).thenReturn(new ReimbursementConfigurationDTO(
				carMileageRate,
				dailyAllowanceRate
		));
		final SubmitReimbursementRequest request = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(11))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(request);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("167.31"));
	}

	@Test
	void should_submit_trip_reimbursement_request_calculating_every_reimbursement_base() {
		// given
		final Money carMileageRate = new Money("10");
		final Money dailyAllowanceRate = new Money("1");
		when(provider.getReimbursementConfiguration()).thenReturn(new ReimbursementConfigurationDTO(
				carMileageRate,
				dailyAllowanceRate
		));
		final SubmitReimbursementRequest command = SubmitReimbursementRequestSample.builder()
				.carMileage(new CarMileage(15))
				.daysOfAllowance(new DaysOfAllowance(4))
				.build();

		// when
		final ReimbursementRequestResult result = tut.submitReimbursementRequest(command);

		// then
		assertThat(result.getTotalReimbursementAmount()).isEqualTo(new Money("154.00"));
	}


	@Test
	void cannot_submit_reuqest_when_limits_are_reached() {
		// given

		// when

		// then
	}

}