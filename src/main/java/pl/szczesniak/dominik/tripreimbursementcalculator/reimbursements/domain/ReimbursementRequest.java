package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.CarUsage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TimeRange;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;

import java.util.List;
import java.util.Objects;

public class ReimbursementRequest {

	private final ReimbursementId reimbursementId;

	private final TripDate tripDate;

	private final List<ReceiptType> receiptTypes;

	private final CarUsage carUsage;

	private final TimeRange timeRange;

	public ReimbursementRequest(final ReimbursementId reimbursementId, final TripDate tripDate, final List<ReceiptType> receiptTypes, final CarUsage carUsage, final TimeRange timeRange) {
		this.reimbursementId = reimbursementId;
		this.tripDate = tripDate;
		this.receiptTypes = receiptTypes;
		this.carUsage = carUsage;
		this.timeRange = timeRange;
	}

	public ReimbursementId getReimbursementId() {
		return reimbursementId;
	}

	public TripDate getTripDate() {
		return tripDate;
	}

	public List<ReceiptType> getReceipts() {
		return receiptTypes;
	}

	public CarUsage getCarUsage() {
		return carUsage;
	}

	TimeRange getTimeRange() {
		return timeRange;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReimbursementRequest that = (ReimbursementRequest) o;
		return Objects.equals(reimbursementId, that.reimbursementId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbursementId);
	}

	@Override
	public String toString() {
		return "Reimbursement{" +
				"reimbursementId=" + reimbursementId +
				", tripDate=" + tripDate +
				", receipts=" + receiptTypes +
				'}';
	}

}
