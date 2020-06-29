package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto extends BaseUserDto {

	public static final String CONTENT_TYPE = "application/user.registration.v1+json";

	/** Regex to check that password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character and minimum 8 characters total */
	public static final String PASSWORD_POLICY_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\x21-\\x7E&&[^a-zA-Z\\d]]).{8,}";

	@Size(min = 8) // is this needed? regex will also check size
	@NotBlank
	@Pattern(regexp = PASSWORD_POLICY_REGEX)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
