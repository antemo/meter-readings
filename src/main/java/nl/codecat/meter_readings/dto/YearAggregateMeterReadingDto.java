package nl.codecat.meter_readings.dto;

public class YearAggregateMeterReadingDto {

	public static final String CONTENT_TYPE = "application/aggregate.meter.reading.v1+json";

	private Integer year;

	private int reading; // defaults to 0

	public Integer getYear() {
		return year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(final int reading) {
		this.reading = reading;
	}
}
