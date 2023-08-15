package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands.FillReimbursement;

import java.time.LocalDate;
import java.util.Optional;

public class ReimbursementService {

	private final ReimbursementsRepository repository;

	ReimbursementService(final ReimbursementsRepository repository) {
		this.repository = repository;
	}

	public ReimbursementId fillReimbursement(final FillReimbursement command) {
		final ReimbursementRequest reimbursement = new ReimbursementRequest(repository.nextReimbursementId(), command.getTripDate(), command.getReceipts());
		repository.create(reimbursement);
		return reimbursement.getReimbursementId();
	}

	public Optional<ReimbursementRequest> findReimbursement(final ReimbursementId id) {
		return repository.findBy(id);
	}
}
