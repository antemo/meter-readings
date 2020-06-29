package nl.codecat.meter_readings.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;

public class ErrorDto {

	public static final MediaType MEDIA_TYPE = new MediaType("application", "error.v1+json");

	private String error;

	@JsonProperty("error_description") // to match OAuth2 error format
	private String errorDescription;

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(final String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
