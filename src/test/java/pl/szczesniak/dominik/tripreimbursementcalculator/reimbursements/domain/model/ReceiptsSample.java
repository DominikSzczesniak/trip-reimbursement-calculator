package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsSample {

	public static List<Receipt> createAnyReceipts() {
		List<Receipt> receipts = new ArrayList<>();

		receipts.add(new Receipt(1, "Grocery"));
		receipts.add(new Receipt(2, "Clothing"));
		receipts.add(new Receipt(3, "Electronics"));

		return receipts;
	}

}
