package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class NewMeterReadingDto extends BaseMeterReadingDto {

	public static final String CONTENT_TYPE = "application/new.meter.reading.v1+json";

	@NotNull
	private UUID meterId;

	public UUID getMeterId() {
		return meterId;
	}

	public void setMeterId(final UUID meterId) {
		this.meterId = meterId;
	}
}
