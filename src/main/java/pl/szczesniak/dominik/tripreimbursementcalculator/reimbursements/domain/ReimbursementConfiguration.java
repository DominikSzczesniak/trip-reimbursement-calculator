package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration.CarUsageRatePerKilometer;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration.DailyAllowanceRateDaily;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration.TotalReimbursement;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.configuration.TravelDistance;

import java.util.List;

public class ReimbursementConfiguration {

	private CarUsageRatePerKilometer carUsageRatePerKilometer;

	private DailyAllowanceRateDaily dailyAllowanceRateDaily;

	private final List<ReceiptType> receiptTypes;

	private final TotalReimbursement totalReimbursement;

	private final TravelDistance travelDistance;

	public ReimbursementConfiguration(final List<ReceiptType> receiptTypes, final TotalReimbursement totalReimbursement, final TravelDistance travelDistance) {
		this.receiptTypes = receiptTypes;
		this.totalReimbursement = totalReimbursement;
		this.travelDistance = travelDistance;
		this.carUsageRatePerKilometer = new CarUsageRatePerKilometer(0.3);
		this.dailyAllowanceRateDaily = new DailyAllowanceRateDaily(15);
	}

	public ReimbursementConfiguration(final CarUsageRatePerKilometer carUsageRatePerKilometer,
							   final DailyAllowanceRateDaily dailyAllowanceRateDaily,
							   final List<ReceiptType> receiptTypes,
							   final TotalReimbursement totalReimbursement,
							   final TravelDistance travelDistance) {
		this.carUsageRatePerKilometer = carUsageRatePerKilometer;
		this.dailyAllowanceRateDaily = dailyAllowanceRateDaily;
		this.receiptTypes = receiptTypes;
		this.totalReimbursement = totalReimbursement;
		this.travelDistance = travelDistance;
	}

	CarUsageRatePerKilometer getCarUsageRatePerKilometer() {
		return carUsageRatePerKilometer;
	}

	DailyAllowanceRateDaily getDailyAllowanceRateDaily() {
		return dailyAllowanceRateDaily;
	}

	List<ReceiptType> getReceiptTypes() {
		return receiptTypes;
	}

	TotalReimbursement getTotalReimbursement() {
		return totalReimbursement;
	}

	TravelDistance getTravelDistance() {
		return travelDistance;
	}
}
