package nl.codecat.meter_readings.service;

import nl.codecat.meter_readings.entity.domain.Client;
import nl.codecat.meter_readings.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ClientService extends AbstractService<Client, UUID> {

	private final ClientRepository repository;

	@Autowired
	public ClientService(final ClientRepository repository) {
		this.repository = repository;
	}

	@Override
	protected JpaRepository<Client, UUID> getRepository() {
		return repository;
	}
}
