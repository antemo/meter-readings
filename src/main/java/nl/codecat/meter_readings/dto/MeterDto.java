package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public class MeterDto {

	public static final String CONTENT_TYPE = "application/meter.v1+json";

	private UUID id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(max = 50)
	private String serialNumber;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
