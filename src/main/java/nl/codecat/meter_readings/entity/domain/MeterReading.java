package nl.codecat.meter_readings.entity.domain;

import nl.codecat.meter_readings.entity.BaseEntity;

import javax.persistence.*;
import java.time.Month;
import java.util.Objects;

@Entity
@Table(name = "meter_reading")
public class MeterReading extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "meter_id", nullable = false)
	private Meter meter;

	@Column(name = "year", nullable = false)
	private int year;

	@Enumerated(EnumType.STRING)
	@Column(name = "month", length = 20, nullable = false)
	private Month month;

	@Column(name = "reading", length = 20, nullable = false)
	private int reading;

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(final Meter meter) {
		this.meter = meter;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
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
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		if (!super.equals(other)) {
			return false;
		}
		final MeterReading that = (MeterReading) other;
		return Objects.equals(getMeter(), that.getMeter()) && getMonth() == that.getMonth();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getMeter(), getMonth());
	}
}
