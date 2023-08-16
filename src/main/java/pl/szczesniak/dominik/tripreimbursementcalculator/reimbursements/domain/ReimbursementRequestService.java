package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands.FillReimbursement;

import java.util.Optional;

public class ReimbursementRequestService {

	private final ReimbursementsRepository repository;

	ReimbursementRequestService(final ReimbursementsRepository repository) {
		this.repository = repository;
	}

	public ReimbursementId fillReimbursement(final FillReimbursement command) {
		final ReimbursementRequest reimbursement = new ReimbursementRequest(
				repository.nextReimbursementId(),
				command.getTripDate(),
				command.getReceipts(),
				command.getCarUsage(),
				command.getTimeRange()
		);
		repository.create(reimbursement);
		return reimbursement.getReimbursementId();
	}

	public Optional<ReimbursementRequest> findReimbursement(final ReimbursementId id) {
		return repository.findBy(id);
	}
}
