package nl.codecat.meter_readings.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationErrorDto extends ErrorDto {

	public static final MediaType MEDIA_TYPE = new MediaType("application", "validation.error.v1+json");

	private final List<ValidationError> validationErrors = new ArrayList<>();

	public void addValidationError(final ValidationError validationError) {
		Assert.notNull(validationError, "Validation error to add can't be null");

		validationErrors.add(validationError);
	}

	public List<ValidationError> getValidationErrors() {
		return Collections.unmodifiableList(validationErrors);
	}

	public static class ValidationError {

		private final String message;

		private final String field;

		private final Object rejectedValue;

		// @formatter:off
		@JsonCreator
		public ValidationError(@JsonProperty("message") final String message,
							   @JsonProperty("field") final String field,
							   @JsonProperty("rejectedValue") final Object rejectedValue) {
			this.message = message;
			this.field = field;
			this.rejectedValue = rejectedValue;
		}
		// @formatter:off

		public String getMessage() {
			return message;
		}

		public String getField() {
			return field;
		}

		public Object getRejectedValue() {
			return rejectedValue;
		}
	}
}
