package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.CarUsage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TimeRange;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands.FillReimbursement;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.infrastructure.adapters.outgoing.InMemoryReimbursementsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.CarUsageSample.createAnyCarUsage;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptsSample.createAnyReceipts;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDateSample.createAnyTripDate;

class ReimbursementRequestServiceTest {

	private ReimbursementRequestService tut;

	@BeforeEach
	void setUp() {
		tut = new ReimbursementRequestService(new InMemoryReimbursementsRepository());
	}

	@Test
	void should_save_reimbursement_request() {
		// given
		final List<ReceiptType> receiptTypes = createAnyReceipts();
		final TripDate tripDate = createAnyTripDate();
		final CarUsage carUsage = createAnyCarUsage();
		final TimeRange timeRange = new TimeRange(LocalDate.of(2023, 8, 10), LocalDate.now());
		final ReimbursementId id = tut.fillReimbursement(new FillReimbursement(
				tripDate,
				receiptTypes,
				carUsage,
				timeRange
		));

		// when
		final Optional<ReimbursementRequest> foundReimbursement = tut.findReimbursement(id);

		// then
		assertThat(foundReimbursement.isPresent()).isTrue();

		final ReimbursementRequest reimbursementRequest = foundReimbursement.get();
		assertThat(reimbursementRequest.getReceipts()).isEqualTo(receiptTypes);
		assertThat(reimbursementRequest.getTripDate()).isEqualTo(tripDate);
		assertThat(reimbursementRequest.getCarUsage()).isEqualTo(carUsage);
		assertThat(reimbursementRequest.getTimeRange()).isEqualTo(timeRange);
	}

}