package nl.codecat.meter_readings.service;

import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.entity.domain.MeterReading;
import nl.codecat.meter_readings.repository.MeterReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MeterReadingService extends AbstractService<MeterReading, UUID> {

	private final MeterReadingRepository repository;

	@Autowired
	public MeterReadingService(final MeterReadingRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<MeterReading> getAll(final Meter meter) {
		return repository.findByMeter(meter);
	}

	@Transactional(readOnly = true)
	public List<MeterReading> getAll(final Meter meter, final int year) {
		return repository.findByMeterAndYear(meter, year);
	}

	@Transactional(readOnly = true)
	public Optional<MeterReading> get(final Meter meter, final int year, final Month month) {
		return repository.findByMeterAndYearAndMonth(meter, year, month);
	}

	@Override
	protected JpaRepository<MeterReading, UUID> getRepository() {
		return repository;
	}
}
