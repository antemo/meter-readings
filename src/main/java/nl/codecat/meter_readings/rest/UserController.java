package nl.codecat.meter_readings.rest;

import nl.codecat.meter_readings.dto.UserDto;
import nl.codecat.meter_readings.entity.security.User;
import nl.codecat.meter_readings.exception.MeterReaderExceptionUtil;
import nl.codecat.meter_readings.service.UserService;
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
@RequestMapping(UserController.BASE_USERS_URL)
public class UserController {

	public static final String BASE_USERS_URL = "/users";

	private final UserService userService;

	private final ModelMapper modelMapper;

	@Autowired
	public UserController(final UserService userService, final ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = UserDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('USER_VIEW', 'USER_EDIT')")
	public List<UserDto> getAllUsers() {
		return userService.getAll().stream().map(entity -> modelMapper.map(entity, UserDto.class)).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{id}", produces = UserDto.CONTENT_TYPE)
	@PreAuthorize("hasAnyAuthority('USER_VIEW', 'USER_EDIT')")
	public UserDto getUser(@PathVariable final UUID id) {
		// @formatter:off
		return userService.findById(id)
						  .map(entity -> modelMapper.map(entity, UserDto.class))
						  .orElseThrow(() -> MeterReaderExceptionUtil.noUser(id));
		// @formatter:on
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(consumes = UserDto.CONTENT_TYPE)
	@PreAuthorize("hasAuthority('USER_EDIT')")
	public void updateUser(@RequestBody @Valid final UserDto userDto) {
		// User has separate DTO for update, so validation that ID is set is done by framework

		final User user = userService.findById(userDto.getId()).orElseThrow(() -> MeterReaderExceptionUtil.noUser(userDto.getId()));
		modelMapper.map(userDto, user);
		userService.update(user);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('USER_EDIT')")
	public void deleteUser(@PathVariable final UUID id) {
		final Optional<User> user = userService.findById(id);

		if (user.isPresent()) {
			userService.delete(user.get());
		} else {
			throw MeterReaderExceptionUtil.noUser(id);
		}
	}
}
