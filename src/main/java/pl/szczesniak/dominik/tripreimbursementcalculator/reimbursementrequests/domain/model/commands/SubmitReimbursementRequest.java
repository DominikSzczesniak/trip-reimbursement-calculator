package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;

import java.util.Optional;

public class SubmitReimbursementRequest {

	private final TripDate tripDate;

	private final CarMileage carMileage;

	private final DaysOfAllowance daysOfAllowance;

	public SubmitReimbursementRequest(final TripDate tripDate, final CarMileage carMileage, final DaysOfAllowance daysOfAllowance) {
		this.tripDate = tripDate;
		this.carMileage = carMileage;
		this.daysOfAllowance = daysOfAllowance;
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

}
