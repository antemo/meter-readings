package nl.codecat.meter_readings.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception that all custom exceptions in Meter reader application should extend.<br>
 * There is separate method in {@link RestExceptionsAdvice} to handle this exception specifically.
 */
public class BaseMeterReaderException extends RuntimeException {

	private static final long serialVersionUID = -7205051506938562560L;

	private final String error;

	private final String errorDescription;

	private final HttpStatus httpStatus;

	public BaseMeterReaderException(final String error, final String errorDescription) {
		this(error, errorDescription, HttpStatus.INTERNAL_SERVER_ERROR, null);
	}

	public BaseMeterReaderException(final String error, final String errorDescription, final HttpStatus httpStatus) {
		this(error, errorDescription, httpStatus, null);
	}

	public BaseMeterReaderException(final String error, final String errorDescription, final HttpStatus httpStatus, final Throwable cause) {
		super(cause);
		this.error = error;
		this.errorDescription = errorDescription;
		this.httpStatus = httpStatus;
	}

	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
