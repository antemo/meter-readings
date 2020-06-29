package nl.codecat.meter_readings.entity.domain;

import nl.codecat.meter_readings.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client extends BaseEntity {

	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "address_id", nullable = false, unique = true)
	// unique=true to ensure every client has different instance of Address
	private Address address;

	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "meter_id", nullable = false, unique = true)
	// unique=true to ensure every client has different instance of Meter
	private Meter meter;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(final Meter meter) {
		this.meter = meter;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		final Client client = (Client) other;
		return Objects.equals(getName(), client.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
