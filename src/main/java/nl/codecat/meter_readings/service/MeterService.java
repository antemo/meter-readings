package nl.codecat.meter_readings.service;

import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class MeterService extends AbstractService<Meter, UUID> {

	private final MeterRepository repository;

	@Autowired
	public MeterService(final MeterRepository repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Meter, UUID> getRepository() {
		return repository;
	}
}
