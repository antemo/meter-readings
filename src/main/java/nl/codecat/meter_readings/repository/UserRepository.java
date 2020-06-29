package nl.codecat.meter_readings.repository;

import nl.codecat.meter_readings.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsername(final String username);
}
