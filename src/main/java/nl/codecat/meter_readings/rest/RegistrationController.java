package nl.codecat.meter_readings.rest;

import nl.codecat.meter_readings.dto.UserDto;
import nl.codecat.meter_readings.dto.UserRegistrationDto;
import nl.codecat.meter_readings.entity.security.User;
import nl.codecat.meter_readings.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

	public static final String REGISTRATION_URL = "/register";

	private final UserService userService;

	private final ModelMapper modelMapper;

	@Autowired
	public RegistrationController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = REGISTRATION_URL, consumes = UserRegistrationDto.CONTENT_TYPE, produces = UserDto.CONTENT_TYPE)
	public UserDto register(@RequestBody @Valid final UserRegistrationDto userDto) {
		final User newUser = modelMapper.map(userDto, User.class);
		final User result = userService.create(newUser);

		return modelMapper.map(result, UserDto.class);
	}
}
