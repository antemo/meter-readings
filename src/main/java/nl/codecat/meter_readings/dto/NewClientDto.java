package nl.codecat.meter_readings.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewClientDto {

	public static final String CONTENT_TYPE = "application/new.client.v1+json";

	@NotBlank
	@Size(max = 50)
	private String name;

	@Valid
	private AddressDto address;

	@Valid
	private MeterDto meter;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(final AddressDto address) {
		this.address = address;
	}

	public MeterDto getMeter() {
		return meter;
	}

	public void setMeter(final MeterDto meter) {
		this.meter = meter;
	}
}
