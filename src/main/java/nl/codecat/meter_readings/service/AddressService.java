package nl.codecat.meter_readings.service;

import nl.codecat.meter_readings.entity.domain.Address;
import nl.codecat.meter_readings.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AddressService extends AbstractService<Address, UUID> {

	private final AddressRepository repository;

	@Autowired
	public AddressService(final AddressRepository repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Address, UUID> getRepository() {
		return repository;
	}
}
