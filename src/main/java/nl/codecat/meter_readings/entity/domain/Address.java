package nl.codecat.meter_readings.entity.domain;

import nl.codecat.meter_readings.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

	@Column(name = "address", length = 200, nullable = false)
	@SuppressWarnings("PMD.AvoidFieldNameMatchingTypeName")
	private String address;

	@Column(name = "postal_code", length = 20, nullable = false)
	private String postalCode;

	@Column(name = "city", length = 100, nullable = false)
	private String city;

	@Column(name = "country", length = 100, nullable = false)
	private String country;

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
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
		final Address address1 = (Address) other;
		return Objects.equals(getAddress(), address1.getAddress()) && Objects.equals(getCity(), address1.getCity()) && Objects.equals(getCountry(), address1.getCountry());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getAddress(), getCity(), getCountry());
	}
}
