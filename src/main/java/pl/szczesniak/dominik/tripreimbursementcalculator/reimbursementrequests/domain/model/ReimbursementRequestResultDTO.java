package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementrequests.domain.model;

import java.math.BigDecimal;

public class ReimbursementRequestResultDTO {

		private final String id;
		private final BigDecimal totalReimbursementAmount;

		public ReimbursementRequestResultDTO(String id, BigDecimal totalReimbursementAmount) {
			this.id = id;
			this.totalReimbursementAmount = totalReimbursementAmount;
		}

		public String getId() {
			return id;
		}

		public BigDecimal getTotalReimbursementAmount() {
			return totalReimbursementAmount;
		}
	}