package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequest;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.ReimbursementRequestsRepository;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryReimbursementRequestsRepository implements ReimbursementRequestsRepository {

	private final Map<ReimbursementId, ReimbursementRequest> reimbursementRequests = new HashMap<>();

	@Override
	public ReimbursementId create(final ReimbursementRequest reimbursementRequest) {
		reimbursementRequests.put(reimbursementRequest.getReimbursementId(), reimbursementRequest);
		return reimbursementRequest.getReimbursementId();
	}

	@Override
	public Optional<ReimbursementRequest> findBy(final ReimbursementId reimbursementId) {
		return Optional.ofNullable(reimbursementRequests.get(reimbursementId));
	}

}
