package nl.codecat.meter_readings.rest;

import nl.codecat.meter_readings.dto.ClientDto;
import nl.codecat.meter_readings.dto.NewClientDto;
import nl.codecat.meter_readings.entity.domain.Address;
import nl.codecat.meter_readings.entity.domain.Client;
import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.exception.MeterReaderExceptionUtil;
import nl.codecat.meter_readings.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ClientController.BASE_USERS_URL)
public class ClientController {

	public static final String BASE_USERS_URL = "/clients";

	private final ClientService clientService;

	private final ModelMapper modelMapper;

	@Autowired
	public ClientController(final ClientService clientService, final ModelMapper modelMapper) {
		this.clientService = clientService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = ClientDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('CLIENT_VIEW', 'CLIENT_EDIT')")
	public List<ClientDto> getAll() {
		return clientService.getAll().stream().map(entity -> modelMapper.map(entity, ClientDto.class)).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{id}", produces = ClientDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('CLIENT_VIEW', 'CLIENT_EDIT')")
	public ClientDto getClient(@PathVariable final UUID id) {
		// @formatter:off
		return clientService.findById(id)
						  .map(entity -> modelMapper.map(entity, ClientDto.class))
						  .orElseThrow(() -> MeterReaderExceptionUtil.noClient(id));
		// @formatter:on
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = NewClientDto.CONTENT_TYPE, produces = ClientDto.CONTENT_TYPE)
	@PreAuthorize("hasAuthority('CLIENT_EDIT')")
	public ClientDto createClient(@RequestBody @Valid final NewClientDto newClientDto) {
		final Client client = new Client();
		client.setName(newClientDto.getName());
		client.setAddress(modelMapper.map(newClientDto.getAddress(), Address.class));
		client.setMeter(modelMapper.map(newClientDto.getMeter(), Meter.class));

		final Client result = clientService.create(client);

		return modelMapper.map(result, ClientDto.class);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(consumes = ClientDto.CONTENT_TYPE)
	@PreAuthorize("hasAuthority('CLIENT_EDIT')")
	public void updateClient(@RequestBody @Valid final ClientDto clientDto) {
		final Client client = clientService.findById(clientDto.getId()).orElseThrow(() -> MeterReaderExceptionUtil.noClient(clientDto.getId()));
		modelMapper.map(clientDto, client);
		clientService.update(client);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('CLIENT_EDIT')")
	public void deleteClient(@PathVariable final UUID id) {
		final Optional<Client> client = clientService.findById(id);

		if (client.isPresent()) {
			clientService.delete(client.get());
		} else {
			throw MeterReaderExceptionUtil.noClient(id);
		}
	}
}
