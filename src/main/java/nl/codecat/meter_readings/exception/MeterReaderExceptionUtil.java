package nl.codecat.meter_readings.exception;

import org.springframework.http.HttpStatus;

import java.time.Month;
import java.util.UUID;

/**
 * Utility class for creating {@link BaseMeterReaderException}s.
 * Main goal of this utility is to improve code readability and make tracking of error keys easier, since all of them will be in same place.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MeterReaderExceptionUtil {

	private MeterReaderExceptionUtil() {
		// utility class
	}

	public static BaseMeterReaderException noUser(final UUID userId) {
		return new BaseMeterReaderException("user_not_found", "User with ID " + userId + " doesn't exist", HttpStatus.NOT_FOUND);
	}

	public static BaseMeterReaderException databaseConstraintViolation(final String message) {
		return new BaseMeterReaderException("database_constraint", message, HttpStatus.BAD_REQUEST);
	}

	public static BaseMeterReaderException noMeter(final UUID meterId) {
		return new BaseMeterReaderException("meter_not_found", "Meter with ID " + meterId + " doesn't exist", HttpStatus.NOT_FOUND);
	}

	public static BaseMeterReaderException noClient(final UUID clientId) {
		return new BaseMeterReaderException("client_not_found", "Client with ID " + clientId + " doesn't exist", HttpStatus.NOT_FOUND);
	}

	public static BaseMeterReaderException noMeterReading(final UUID meterReadingId) {
		return new BaseMeterReaderException("meter_reading_not_found", "Meter reading with ID " + meterReadingId + " doesn't exist", HttpStatus.NOT_FOUND);
	}

	public static BaseMeterReaderException noMeterReading(final UUID meterId, final int year, final Month month) {
		return new BaseMeterReaderException("no_meter_reading", "There is no meter reading for meter with ID " + meterId + ", year " + year + " and month " + month, HttpStatus.NOT_FOUND);
	}
}
