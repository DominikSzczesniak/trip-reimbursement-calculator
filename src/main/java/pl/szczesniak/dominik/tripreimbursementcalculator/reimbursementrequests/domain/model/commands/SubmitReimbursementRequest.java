package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNullElseGet;

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
		this.carMileage = requireNonNullElseGet(carMileage, () -> new CarMileage(0.00));
		this.daysOfAllowance = requireNonNullElseGet(daysOfAllowance, () -> new DaysOfAllowance(0));
		this.receipts = requireNonNullElseGet(receipts, ArrayList::new);
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
