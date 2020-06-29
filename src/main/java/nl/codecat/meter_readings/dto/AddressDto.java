package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddressDto {

	public static final String CONTENT_TYPE = "application/address.v1+json";

	@NotBlank
	@Size(max = 200)
	private String address;

	@NotBlank
	@Size(max = 20)
	private String postalCode;

	@NotBlank
	@Size(max = 100)
	private String city;

	@NotBlank
	@Size(max = 100)
	private String country;

	// suppress CPD since it detects getters and setters from entity this DTO represents
	@SuppressWarnings("CPD-START")
	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	@SuppressWarnings("CPD-END")
	public void setCountry(final String country) {
		this.country = country;
	}
}
