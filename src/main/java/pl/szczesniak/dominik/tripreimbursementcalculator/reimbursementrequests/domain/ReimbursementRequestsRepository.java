package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;

import java.util.Optional;

public interface ReimbursementRequestsRepository {

	ReimbursementId create(ReimbursementRequest reimbursementRequest);

	Optional<ReimbursementRequest> findBy(ReimbursementId reimbursementId);

}
