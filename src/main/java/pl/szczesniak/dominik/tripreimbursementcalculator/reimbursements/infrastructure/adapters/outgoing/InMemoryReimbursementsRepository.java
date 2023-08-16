package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.infrastructure.adapters.outgoing;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.ReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.ReimbursementsRepository;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.reimbursementrequest.ReimbursementId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryReimbursementsRepository implements ReimbursementsRepository {


	private final Map<ReimbursementId, ReimbursementRequest> reimbursementsRequests = new HashMap<>();

	@Override
	public ReimbursementId nextReimbursementId() {
		return new ReimbursementId(UUID.randomUUID());
	}

	@Override
	public void create(final ReimbursementRequest reimbursement) {
		reimbursementsRequests.put(reimbursement.getReimbursementId(), reimbursement);
	}

	@Override
	public Optional<ReimbursementRequest> findBy(final ReimbursementId reimbursementId) {
		return reimbursementsRequests.values().stream()
				.filter(reimbursement -> reimbursement.getReimbursementId().equals(reimbursementId))
				.findFirst();
	}

}
