package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.CarUsage;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TimeRange;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;

import java.util.List;

public class FillReimbursement {


	private final TripDate tripDate;

	private final List<ReceiptType> receiptTypes;

	private final CarUsage carUsage;

	private final TimeRange timeRange;

	public FillReimbursement(final TripDate tripDate, final List<ReceiptType> receiptTypes, final CarUsage carUsage, final TimeRange timeRange) {
		this.tripDate = tripDate;
		this.receiptTypes = receiptTypes;
		this.carUsage = carUsage;
		this.timeRange = timeRange;
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

	public TimeRange getTimeRange() {
		return timeRange;
	}

}
