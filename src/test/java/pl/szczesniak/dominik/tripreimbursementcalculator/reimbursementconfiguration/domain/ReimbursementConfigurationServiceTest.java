package pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.tripreimbursementcalculator.money.domain.model.Money;
import pl.szczesniak.dominik.tripreimbursementcalculator.receipt.domain.model.ReceiptType;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReceiptTypeReimbursementLimit;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.domain.model.ReimbursementConfiguration;
import pl.szczesniak.dominik.tripreimbursementcalculator.reimbursementconfiguration.infrastructure.adapters.persistence.InMemoryReimbursementConfigurationRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReimbursementConfigurationServiceTest {

	private ReimbursementConfigurationService tut;

	@BeforeEach
	void setUp() {
		tut = new ReimbursementConfigurationService(new InMemoryReimbursementConfigurationRepository());
	}

	@Test
	void should_save_and_get_repository() {
		// given
		final ReimbursementConfiguration configuration = ReimbursementConfiguration.builder()
				.carMileageRate(new Money("15"))
				.dailyAllowanceRate(new Money("1"))
				.receipts(List.of(new ReceiptTypeReimbursementLimit(new ReceiptType("Uber"), new Money("10"))))
				.distancePriceLimit(new Money("100"))
				.totalReimbursementLimit(new Money("10000"))
				.build();

		// when
		tut.setReimbursementConfiguration(configuration);
		final ReimbursementConfiguration reimbursementConfiguration = tut.getReimbursementConfiguration();

		// then
		assertThat(reimbursementConfiguration).isEqualTo(configuration);
		assertThat(reimbursementConfiguration.getReceipts()).isEqualTo(configuration.getReceipts());
		assertThat(reimbursementConfiguration.getDailyAllowanceRate()).isEqualTo(configuration.getDailyAllowanceRate());
		assertThat(reimbursementConfiguration.getDistancePriceLimit()).isEqualTo(configuration.getDistancePriceLimit());
		assertThat(reimbursementConfiguration.getTotalReimbursementLimit()).isEqualTo(configuration.getTotalReimbursementLimit());
	}

	@Test
	void should_find_latest_configuration() {
		// given
		final ReimbursementConfiguration firstConfiguration = ReimbursementConfiguration.builder()
				.totalReimbursementLimit(new Money("1000"))
				.build();
		tut.setReimbursementConfiguration(firstConfiguration);
		final ReimbursementConfiguration lastConfiguration = ReimbursementConfiguration.builder().build();
		tut.setReimbursementConfiguration(lastConfiguration);

		// when
		final ReimbursementConfiguration foundConfiguration = tut.getReimbursementConfiguration();

		// then
		assertThat(foundConfiguration).isNotEqualTo(firstConfiguration);
		assertThat(foundConfiguration).isEqualTo(lastConfiguration);
	}

	@Test
	void should_have_default_values_when_no_configurations_set() {
		// when
		final ReimbursementConfiguration lastReimbursementConfiguration = tut.getReimbursementConfiguration();

		// then
		assertThat(lastReimbursementConfiguration.getCarMileageRate().get()).isEqualTo(new Money("0.3"));
		assertThat(lastReimbursementConfiguration.getDailyAllowanceRate().get()).isEqualTo(new Money("15"));
	}
}