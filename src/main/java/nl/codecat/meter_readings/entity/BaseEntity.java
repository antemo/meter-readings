package nl.codecat.meter_readings.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@CreatedDate
	@Column(name = "created", updatable = false)
	public Instant created;

	@CreatedBy
	@Column(name = "created_by", length = 100)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "last_modification")
	private Instant lastModification;

	@LastModifiedBy
	@Column(name = "last_modified_by", length = 100)
	private String lastModifiedBy;

	public UUID getId() {
		return id;
	}

	public Instant getCreated() {
		return created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Instant getLastModification() {
		return lastModification;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		final BaseEntity other = (BaseEntity) object;

		return Objects.equals(id, other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "id = " + id;
	}
}
