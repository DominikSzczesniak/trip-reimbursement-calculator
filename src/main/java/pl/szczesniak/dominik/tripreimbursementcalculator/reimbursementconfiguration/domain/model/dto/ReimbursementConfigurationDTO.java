package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.dto;

import java.util.List;

public class ReimbursementConfigurationDTO {

	private String carMileageRate;

	private String dailyAllowanceRate;

	private String totalReimbursementLimit;

	private String distancePriceLimit;

	private List<ReceiptTypeReimbursementLimitDTO> receipts;

	public ReimbursementConfigurationDTO(final String carMileageRate,
										 final String dailyAllowanceRate,
										 final String totalReimbursementLimit,
										 final String distancePriceLimit,
										 final List<ReceiptTypeReimbursementLimitDTO> receipts) {
		this.carMileageRate = carMileageRate;
		this.dailyAllowanceRate = dailyAllowanceRate;
		this.totalReimbursementLimit = totalReimbursementLimit;
		this.distancePriceLimit = distancePriceLimit;
		this.receipts = receipts;
	}

	public ReimbursementConfigurationDTO() {
	}

	public String getCarMileageRate() {
		return carMileageRate;
	}

	public String getDailyAllowanceRate() {
		return dailyAllowanceRate;
	}

	public String getTotalReimbursementLimit() {
		return totalReimbursementLimit;
	}

	public String getDistancePriceLimit() {
		return distancePriceLimit;
	}

	public List<ReceiptTypeReimbursementLimitDTO> getReceipts() {
		return receipts;
	}

}
