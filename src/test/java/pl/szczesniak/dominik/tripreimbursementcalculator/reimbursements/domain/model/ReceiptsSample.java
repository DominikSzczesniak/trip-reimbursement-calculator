package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsSample {

	public static List<ReceiptType> createAnyReceipts() {
		List<ReceiptType> receiptTypes = new ArrayList<>();

		receiptTypes.add(new ReceiptType("Grocery"));
		receiptTypes.add(new ReceiptType("Clothing"));
		receiptTypes.add(new ReceiptType("Electronics"));

		return receiptTypes;
	}

}
