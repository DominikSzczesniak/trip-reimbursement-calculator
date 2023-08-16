package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.reimbursementrequest.ReimbursementId;

import java.util.Optional;

public interface ReimbursementsRepository {

	ReimbursementId nextReimbursementId();

	void create(ReimbursementRequest reimbursement);

	Optional<ReimbursementRequest> findBy(ReimbursementId reimbursementId);

}
