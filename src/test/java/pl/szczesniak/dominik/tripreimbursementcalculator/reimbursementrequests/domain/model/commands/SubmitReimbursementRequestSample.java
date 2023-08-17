package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.CarMileage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.DaysOfAllowance;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDate;

import static java.util.Optional.ofNullable;
import static pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model.TripDateSample.createAnyTripDate;

public class SubmitReimbursementRequestSample {

	private static SubmitReimbursementRequest build(final TripDate tripDate,
													final CarMileage carMileage,
													final DaysOfAllowance daysOfAllowance) {
		return new SubmitReimbursementRequest(
				ofNullable(tripDate).orElse(createAnyTripDate()),
				carMileage,
				daysOfAllowance
		);
	}

	public static SubmitReimbursementRequestBuilder builder() {
		return new SubmitReimbursementRequestBuilder();
	}

	public static class SubmitReimbursementRequestBuilder {

		private TripDate tripDate;

		private CarMileage carMileage;

		private DaysOfAllowance daysOfAllowance;

		SubmitReimbursementRequestBuilder() {

		}

		public SubmitReimbursementRequestBuilder tripDate(final TripDate tripDate) {
			this.tripDate = tripDate;
			return this;
		}

		public SubmitReimbursementRequestBuilder carMileage(final CarMileage carMileage) {
			this.carMileage = carMileage;
			return this;
		}

		public SubmitReimbursementRequestBuilder daysOfAllowance(final DaysOfAllowance daysOfAllowance) {
			this.daysOfAllowance = daysOfAllowance;
			return this;
		}

		public SubmitReimbursementRequest build() {
			return SubmitReimbursementRequestSample.build(this.tripDate, this.carMileage, this.daysOfAllowance);
		}

		@Override
		public String toString() {
			return "SubmitReimbursementRequestBuilder{" +
					"tripDate=" + tripDate +
					", carUsage=" + carMileage +
					", timeRange=" + daysOfAllowance +
					'}';
		}
	}

}
