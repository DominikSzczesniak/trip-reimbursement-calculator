package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.infrastructure.adapters.outgoing;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.ReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.ReimbursementsRepository;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryReimbursementsRepository implements ReimbursementsRepository {

	private final AtomicInteger nextId = new AtomicInteger(0);

	private final Map<ReimbursementId, ReimbursementRequest> reimbursementsRequests = new HashMap<>();

	@Override
	public ReimbursementId nextReimbursementId() {
		return new ReimbursementId(nextId.incrementAndGet());
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
