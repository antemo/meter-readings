package nl.codecat.meter_readings.rest;

import nl.codecat.meter_readings.dto.MeterReadingDto;
import nl.codecat.meter_readings.dto.NewMeterReadingDto;
import nl.codecat.meter_readings.dto.YearAggregateMeterReadingDto;
import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.entity.domain.MeterReading;
import nl.codecat.meter_readings.exception.MeterReaderExceptionUtil;
import nl.codecat.meter_readings.service.MeterReadingService;
import nl.codecat.meter_readings.service.MeterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(MeterReadingController.BASE_USERS_URL)
public class MeterReadingController {

	public static final String BASE_USERS_URL = "/readings";

	private final MeterReadingService meterReadingService;

	private final MeterService meterService;

	private final ModelMapper modelMapper;

	@Autowired
	public MeterReadingController(final MeterReadingService meterReadingService, final MeterService meterService, final ModelMapper modelMapper) {
		this.meterReadingService = meterReadingService;
		this.meterService = meterService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{meterId}", produces = MeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('METER_READING_VIEW', 'METER_READING_EDIT', 'METER_READING_ADD')")
	public List<MeterReadingDto> getMeterReading(@PathVariable final UUID meterId) {
		final Meter meter = meterService.findById(meterId).orElseThrow(() -> MeterReaderExceptionUtil.noMeter(meterId));

		return meterReadingService.getAll(meter).stream().map(entity -> modelMapper.map(entity, MeterReadingDto.class)).sorted().collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{meterId}/{year}", produces = MeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('METER_READING_VIEW', 'METER_READING_EDIT', 'METER_READING_ADD')")
	public List<MeterReadingDto> getMeterReading(@PathVariable final UUID meterId, @PathVariable final int year) {
		final Meter meter = meterService.findById(meterId).orElseThrow(() -> MeterReaderExceptionUtil.noMeter(meterId));

		return meterReadingService.getAll(meter, year).stream().map(entity -> modelMapper.map(entity, MeterReadingDto.class)).sorted().collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{meterId}/{year}", produces = YearAggregateMeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('METER_READING_VIEW', 'METER_READING_EDIT', 'METER_READING_ADD')")
	public YearAggregateMeterReadingDto getAggregateMeterReading(@PathVariable final UUID meterId, @PathVariable final int year) {
		final Meter meter = meterService.findById(meterId).orElseThrow(() -> MeterReaderExceptionUtil.noMeter(meterId));

		final YearAggregateMeterReadingDto result = new YearAggregateMeterReadingDto();
		result.setYear(year);
		result.setReading(meterReadingService.getAll(meter, year).stream().mapToInt(MeterReading::getReading).sum());

		return result;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{meterId}/{year}/{month}", produces = MeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('METER_READING_VIEW', 'METER_READING_EDIT', 'METER_READING_ADD')")
	public MeterReadingDto getMeterReading(@PathVariable final UUID meterId, @PathVariable final int year, @PathVariable final Month month) {
		final Meter meter = meterService.findById(meterId).orElseThrow(() -> MeterReaderExceptionUtil.noMeter(meterId));

		return meterReadingService.get(meter, year, month).map(entity -> modelMapper.map(entity, MeterReadingDto.class))
								  .orElseThrow(() -> MeterReaderExceptionUtil.noMeterReading(meterId, year, month));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = NewMeterReadingDto.CONTENT_TYPE, produces = MeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAuthority('METER_READING_ADD')")
	public MeterReadingDto createMeterReading(@RequestBody @Valid final NewMeterReadingDto newMeterReadingDto) {
		final MeterReading meterReading = modelMapper.map(newMeterReadingDto, MeterReading.class);
		final MeterReading result = meterReadingService.create(meterReading);

		return modelMapper.map(result, MeterReadingDto.class);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(consumes = MeterReadingDto.CONTENT_TYPE)
	@PreAuthorize("hasAuthority('METER_READING_EDIT')")
	public void updateMeterReading(@RequestBody @Valid final MeterReadingDto meterReadingDto) {
		final MeterReading meterReading = meterReadingService.findById(meterReadingDto.getId()).orElseThrow(() -> MeterReaderExceptionUtil.noMeterReading(meterReadingDto.getId()));
		modelMapper.map(meterReadingDto, meterReading);
		meterReadingService.update(meterReading);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('METER_READING_EDIT')")
	public void deleteMeterReading(@PathVariable final UUID id) {
		final Optional<MeterReading> meterReading = meterReadingService.findById(id);

		if (meterReading.isPresent()) {
			meterReadingService.delete(meterReading.get());
		} else {
			throw MeterReaderExceptionUtil.noMeterReading(id);
		}
	}
}
