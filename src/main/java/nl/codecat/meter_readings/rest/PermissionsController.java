package nl.codecat.meter_readings.rest;

import nl.codecat.meter_readings.entity.security.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(PermissionsController.PERMISSIONS_URL)
public class PermissionsController {

	public static final String PERMISSIONS_URL = "/permissions";

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	// reuse permission, since editing roles will need access to permissions
	@PreAuthorize("hasAuthority('USER_EDIT')")
	public List<Permission> getPermissions() {
		return Arrays.asList(Permission.values());
	}
}
