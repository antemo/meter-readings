package nl.codecat.meter_readings.repository;

import nl.codecat.meter_readings.entity.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

}
