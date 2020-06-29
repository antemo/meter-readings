package nl.codecat.meter_readings.entity.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum Permission implements GrantedAuthority {

	// @formatter:off
	USER_VIEW(1),
	USER_EDIT(2),

	METER_READING_VIEW(11),
	METER_READING_EDIT(12),
	METER_READING_ADD(13),

	CLIENT_VIEW(21),
	CLIENT_EDIT(22)
	;
	// @formatter:on

	private final int id;

	Permission(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getAuthority() {
		return name();
	}

	public static Permission fromId(final int id) {
		for (final Permission permission : Permission.values()) {
			if (permission.getId() == id) {
				return permission;
			}
		}
		throw new IllegalArgumentException("No permission for ID: " + id);
	}

	@Converter
	public static class PermissionConverter implements AttributeConverter<Permission, Integer> {

		public Integer convertToDatabaseColumn(final Permission permission) {
			if (permission == null) {
				return null;
			}

			return permission.getId();
		}

		public Permission convertToEntityAttribute(final Integer id) {
			if (id == null) {
				return null;
			}

			return Permission.fromId(id);
		}
	}
}
