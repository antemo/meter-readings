package nl.codecat.meter_readings.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.Month;

public class BaseMeterReadingDto implements Comparable<BaseMeterReadingDto> {

	@NotNull
	@Positive
	private Integer year;

	@NotNull
	private Month month;

	@PositiveOrZero
	private int reading; // defaults to 0

	public Integer getYear() {
		return year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(final Month month) {
		this.month = month;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(final int reading) {
		this.reading = reading;
	}

	@Override
	public int compareTo(final BaseMeterReadingDto other) {
		final int yearComparison = Integer.compare(year, other.getYear());

		if (yearComparison == 0) {
			return month.compareTo(other.getMonth());
		} else {
			return yearComparison;
		}
	}
}
