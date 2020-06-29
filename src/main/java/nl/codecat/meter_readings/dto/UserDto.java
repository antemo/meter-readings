package nl.codecat.meter_readings.dto;

import nl.codecat.meter_readings.entity.security.Permission;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.*;

public class UserDto extends BaseUserDto {

	public static final String CONTENT_TYPE = "application/user.v1+json";

	@NotNull
	private UUID id;

	private final Set<Permission> permissions = new HashSet<>();

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public void setPermissions(final Collection<Permission> permissions) {
		Assert.notNull(permissions, "Permissions collection can't be null");
		Assert.noNullElements(permissions, "Permissions collection can't hold null values");

		this.permissions.clear();
		this.permissions.addAll(permissions);
	}
}
