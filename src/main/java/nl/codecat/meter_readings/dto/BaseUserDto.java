package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BaseUserDto {

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 100)
	private String fullName;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}
}
