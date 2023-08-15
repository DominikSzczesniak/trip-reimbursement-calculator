package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands.FillReimbursement;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.infrastructure.adapters.outgoing.InMemoryReimbursementsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptsSample.createAnyReceipts;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDateSample.createAnyTripDate;

class ReimbursementServiceTest {

	private ReimbursementService tut;

	@BeforeEach
	void setUp() {
		tut = new ReimbursementService(new InMemoryReimbursementsRepository());
	}

	@Test
	void should_save_reimbursement_request() {
		// given
		final List<Receipt> receipts = createAnyReceipts();
		final TripDate tripDate = createAnyTripDate();
		final ReimbursementId id = tut.fillReimbursement(new FillReimbursement(tripDate, receipts));

		// when
		final Optional<ReimbursementRequest> foundReimbursement = tut.findReimbursement(id);

		// then
		assertThat(foundReimbursement.isPresent()).isTrue();

		final ReimbursementRequest reimbursementRequest = foundReimbursement.get();
		assertThat(reimbursementRequest.getReceipts()).isEqualTo(receipts);
		assertThat(reimbursementRequest.getTripDate()).isEqualTo(tripDate);
	}

}