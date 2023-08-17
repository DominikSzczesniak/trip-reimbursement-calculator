package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReimbursementRequest {

	private final ReimbursementId reimbursementId;

	private final TripDate tripDate;

	private final CarMileage carMileage;

	private final DaysOfAllowance daysOfAllowance;

	private final List<Receipt> receipts;

	public ReimbursementRequest(final ReimbursementId reimbursementId,
								final TripDate tripDate,
								final CarMileage carMileage,
								final DaysOfAllowance daysOfAllowance,
								final List<Receipt> receipts) {
		this.reimbursementId = reimbursementId;
		this.tripDate = tripDate;
		this.carMileage = carMileage;
		this.daysOfAllowance = daysOfAllowance;
		this.receipts = receipts;
	}

	public ReimbursementId getReimbursementId() {
		return reimbursementId;
	}

	public TripDate getTripDate() {
		return tripDate;
	}

	public Optional<CarMileage> getCarMileage() {
		return Optional.ofNullable(carMileage);
	}

	public Optional<DaysOfAllowance> getDaysOfAllowance() {
		return Optional.ofNullable(daysOfAllowance);
	}

	public Optional<List<Receipt>> getReceipts() {
		return Optional.ofNullable(receipts);
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
				'}';
	}

}
