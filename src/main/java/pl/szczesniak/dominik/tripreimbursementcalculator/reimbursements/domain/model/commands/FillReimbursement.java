package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.commands;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;

import java.util.List;

public class FillReimbursement {


	private final TripDate tripDate;

	private final List<Receipt> receipts;

	public FillReimbursement(final TripDate tripDate, final List<Receipt> receipts) {
		this.tripDate = tripDate;
		this.receipts = receipts;
	}


	public TripDate getTripDate() {
		return tripDate;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

}
