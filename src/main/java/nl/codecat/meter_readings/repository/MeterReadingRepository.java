package nl.codecat.meter_readings.repository;

import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.entity.domain.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeterReadingRepository extends JpaRepository<MeterReading, UUID> {

	List<MeterReading> findByMeter(final Meter meter);

	List<MeterReading> findByMeterAndYear(final Meter meter, final int year);

	Optional<MeterReading> findByMeterAndYearAndMonth(final Meter meter, final int year, final Month month);
}
