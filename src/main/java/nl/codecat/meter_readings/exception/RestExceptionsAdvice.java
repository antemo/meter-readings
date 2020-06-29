package nl.codecat.meter_readings.exception;

import nl.codecat.meter_readings.exception.dto.ErrorDto;
import nl.codecat.meter_readings.exception.dto.ValidationErrorDto;
import nl.codecat.meter_readings.exception.dto.ValidationErrorDto.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionsAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionsAdvice.class);

	@ExceptionHandler(BaseMeterReaderException.class)
	public ResponseEntity<ErrorDto> handleMeterReaderException(final BaseMeterReaderException exception) {
		LOGGER.debug("Handling BaseMeterReaderException: {}", exception.getErrorDescription());

		final ErrorDto errorDto = new ErrorDto();
		errorDto.setError(exception.getError());
		errorDto.setErrorDescription(exception.getErrorDescription());

		return createResponse(ErrorDto.MEDIA_TYPE, errorDto, exception.getHttpStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDto> handleAccessDenied(final AccessDeniedException exception) {
		LOGGER.debug("Handling AccessDeniedException:", exception);

		final ErrorDto errorDto = new ErrorDto();
		errorDto.setError("access_denied");
		errorDto.setErrorDescription("Access to requested resource is denied");

		return createResponse(ErrorDto.MEDIA_TYPE, errorDto, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public ResponseEntity<ValidationErrorDto> handleValidationError(final MethodArgumentNotValidException exception) {
		LOGGER.debug("Handling MethodArgumentNotValidException:", exception);

		final BindingResult bindingResult = exception.getBindingResult();

		final ValidationErrorDto validationErrorDto = new ValidationErrorDto();
		validationErrorDto.setError("validation_error");

		validationErrorDto.setErrorDescription(String.format("Validation failed for object=%s Error count: %d", bindingResult.getObjectName(), bindingResult.getErrorCount()));

		for (final FieldError fieldError : bindingResult.getFieldErrors()) {
			validationErrorDto.addValidationError(new ValidationError(fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getRejectedValue()));
		}

		return createResponse(ValidationErrorDto.MEDIA_TYPE, validationErrorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> databaseConstraintViolation(final DataIntegrityViolationException exception) {
		LOGGER.error(exception.getMessage(), exception);

		// DataIntegrityViolationException is caused by ConstraintViolationException, which is in turn caused by PSQLException whose message we need
		if (exception.getCause() == null || exception.getCause().getCause() == null) {
			return handleMeterReaderException(MeterReaderExceptionUtil.databaseConstraintViolation(null));
		}
		return handleMeterReaderException(MeterReaderExceptionUtil.databaseConstraintViolation(exception.getCause().getCause().toString()));
	}

	// TODO add HttpRequestMethodNotSupportedException handling

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleAll(final Exception exception) {
		LOGGER.error("Safety net handling Exception:", exception);

		// this method handles all errors that weren't caught by methods handling specific errors
		// return generic error response
		// TODO add handling of specific exceptions as they are encountered

		final ErrorDto errorDto = new ErrorDto();
		errorDto.setError("unexpected_error");
		errorDto.setErrorDescription("Unexpected error occurred, please contact support");

		return createResponse(ErrorDto.MEDIA_TYPE, errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private <T> ResponseEntity<T> createResponse(final MediaType mediaType, final T body, final HttpStatus httpStatus) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(mediaType);

		return new ResponseEntity<>(body, httpHeaders, httpStatus);
	}
}
