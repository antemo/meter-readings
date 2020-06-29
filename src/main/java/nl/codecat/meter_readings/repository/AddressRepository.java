package nl.codecat.meter_readings.repository;

import nl.codecat.meter_readings.entity.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
