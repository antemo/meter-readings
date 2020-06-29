package nl.codecat.meter_readings.entity.domain;

import nl.codecat.meter_readings.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "meter")
public class Meter extends BaseEntity {

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "serial_number", length = 50, nullable = false, unique = true)
	private String serialNumber;

	// info about connecting to meter (e.g. IP) should also be added here

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
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
		final Meter meter = (Meter) other;
		return Objects.equals(getSerialNumber(), meter.getSerialNumber());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getSerialNumber());
	}
}
