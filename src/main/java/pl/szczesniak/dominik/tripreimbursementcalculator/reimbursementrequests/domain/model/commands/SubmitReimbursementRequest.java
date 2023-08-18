package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SubmitReimbursementRequest {

	private final ReimbursementId id;

	private final TripDate tripDate;

	private final CarMileage carMileage;

	private final DaysOfAllowance daysOfAllowance;

	private final List<Receipt> receipts;

	public SubmitReimbursementRequest(final TripDate tripDate, final CarMileage carMileage,
									  final DaysOfAllowance daysOfAllowance,
									  final List<Receipt> receipts) {
		this.tripDate = tripDate;
		this.carMileage = carMileage;
		this.daysOfAllowance = daysOfAllowance;
		this.receipts = receipts;
		this.id = new ReimbursementId(UUID.randomUUID());
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

	public ReimbursementId getId() {
		return id;
	}
}
