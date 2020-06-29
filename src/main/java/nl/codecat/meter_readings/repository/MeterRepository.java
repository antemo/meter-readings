package nl.codecat.meter_readings.repository;

import nl.codecat.meter_readings.entity.domain.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MeterRepository extends JpaRepository<Meter, UUID> {

	Optional<Meter> findBySerialNumber(final String serialNumber);
}
