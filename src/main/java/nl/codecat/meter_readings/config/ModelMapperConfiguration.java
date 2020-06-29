package nl.codecat.meter_readings.config;

import nl.codecat.meter_readings.dto.*;
import nl.codecat.meter_readings.entity.domain.Address;
import nl.codecat.meter_readings.entity.domain.Client;
import nl.codecat.meter_readings.entity.domain.Meter;
import nl.codecat.meter_readings.entity.domain.MeterReading;
import nl.codecat.meter_readings.entity.security.User;
import nl.codecat.meter_readings.exception.MeterReaderExceptionUtil;
import nl.codecat.meter_readings.service.MeterService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	public ModelMapper modelMapper(final MeterService meterService) {
		final ModelMapper modelMapper = new ModelMapper();

		final TypeMap<UserRegistrationDto, User> userRegistrationTypeMap = modelMapper.createTypeMap(UserRegistrationDto.class, User.class);
		userRegistrationTypeMap.addMappings(mapper -> mapper.skip(User::setPermissions));

		modelMapper.createTypeMap(User.class, UserDto.class);
		final TypeMap<UserDto, User> userTypeMap = modelMapper.createTypeMap(UserDto.class, User.class);
		userTypeMap.addMappings(mapper -> mapper.skip(User::setPassword));

		final TypeMap<NewMeterReadingDto, MeterReading> newMeterReadingTypeMap = modelMapper.createTypeMap(NewMeterReadingDto.class, MeterReading.class);
		newMeterReadingTypeMap.addMappings(mapper -> mapper.using(new MeterIdConverter(meterService)).map(NewMeterReadingDto::getMeterId, MeterReading::setMeter));

		modelMapper.createTypeMap(MeterReading.class, MeterReadingDto.class);

		final TypeMap<MeterReadingDto, MeterReading> meterReadingDtoTypeMap = modelMapper.createTypeMap(MeterReadingDto.class, MeterReading.class);
		meterReadingDtoTypeMap.addMappings(mapper -> mapper.skip(MeterReading::setMeter));

		modelMapper.createTypeMap(AddressDto.class, Address.class);
		modelMapper.createTypeMap(Address.class, AddressDto.class);

		modelMapper.createTypeMap(MeterDto.class, Meter.class);
		modelMapper.createTypeMap(Meter.class, MeterDto.class);

		modelMapper.createTypeMap(Client.class, ClientDto.class);
		modelMapper.createTypeMap(ClientDto.class, Client.class);
		modelMapper.createTypeMap(NewClientDto.class, Client.class);

		// this will throw exception in case there are mapping errors
		modelMapper.validate();

		return modelMapper;
	}

	public static class MeterIdConverter implements Converter<UUID, Meter> {

		private final MeterService meterService;

		public MeterIdConverter(final MeterService meterService) {
			this.meterService = meterService;
		}

		public Meter convert(final MappingContext<UUID, Meter> context) {
			return meterService.findById(context.getSource()).orElseThrow(() -> MeterReaderExceptionUtil.noMeter(context.getSource()));
		}
	}
}
