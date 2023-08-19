package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.dto;

import java.math.BigDecimal;

public class ReceiptTypeReimbursementLimitDTO {

	private String receiptType;
	private BigDecimal receiptLimit;

	public ReceiptTypeReimbursementLimitDTO(String receiptType, BigDecimal receiptLimit) {
		this.receiptType = receiptType;
		this.receiptLimit = receiptLimit;
	}

	public ReceiptTypeReimbursementLimitDTO() {
	}

	public String getReceiptType() {
		return receiptType;
	}

	public BigDecimal getReceiptLimit() {
		return receiptLimit;
	}

}
