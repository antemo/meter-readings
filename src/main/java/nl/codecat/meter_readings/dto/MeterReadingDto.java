package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class MeterReadingDto extends BaseMeterReadingDto {

	public static final String CONTENT_TYPE = "application/meter.reading.v1+json";

	@NotNull
	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}
}
