package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain;

import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.CarUsageRate;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.DailyAllowanceRate;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.Receipt;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.ReimbursementId;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursements.domain.model.TripDate;

import java.util.List;
import java.util.Objects;

public class ReimbursementRequest {

	private final ReimbursementId reimbursementId;

	private final TripDate tripDate;

	private final List<Receipt> receipts;

	private DailyAllowanceRate dailyAllowanceRate = new DailyAllowanceRate(15);

	private CarUsageRate carUsageRate = new CarUsageRate(0.3);

	public ReimbursementRequest(final ReimbursementId reimbursementId, final TripDate tripDate, final List<Receipt> receipts) {
		this.reimbursementId = reimbursementId;
		this.tripDate = tripDate;
		this.receipts = receipts;
	}

	public ReimbursementId getReimbursementId() {
		return reimbursementId;
	}

	public TripDate getTripDate() {
		return tripDate;
	}

	public List<Receipt> getReceipts() {
		return receipts;
	}

	public DailyAllowanceRate getDailyAllowanceRate() {
		return dailyAllowanceRate;
	}

	public CarUsageRate getCarUsageRate() {
		return carUsageRate;
	}

	void setDailyAllowanceRate(final DailyAllowanceRate dailyAllowanceRate) {
		this.dailyAllowanceRate = dailyAllowanceRate;
	}

	void setCarUsageRate(final CarUsageRate carUsageRate) {
		this.carUsageRate = carUsageRate;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ReimbursementRequest that = (ReimbursementRequest) o;
		return Objects.equals(reimbursementId, that.reimbursementId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbursementId);
	}

	@Override
	public String toString() {
		return "Reimbursement{" +
				"reimbursementId=" + reimbursementId +
				", tripDate=" + tripDate +
				", receipts=" + receipts +
				", dailyAllowanceRate=" + dailyAllowanceRate +
				", carUsageRate=" + carUsageRate +
				'}';
	}

}
