package nl.codecat.meter_readings.entity.security;

import nl.codecat.meter_readings.entity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users") // 'user' is reserved keyword
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 383025317257350551L;

	/** All lowercase for getter to match {@link UserDetails} method */
	@Column(name = "user_name", length = 50, unique = true, nullable = false)
	private String username;

	@Column(name = "password", length = 128, nullable = false)
	private String password;

	@Column(name = "full_name", length = 100, nullable = false)
	private String fullName;

	@ElementCollection(fetch = FetchType.EAGER)
	@Convert(converter = Permission.PermissionConverter.class)
	@CollectionTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "permission_id")
	private final Set<Permission> permissions = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public Collection<Permission> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public void setPermissions(final Collection<Permission> permissions) {
		Assert.notNull(permissions, "Permissions collection can't be null");
		Assert.noNullElements(permissions, "Permissions collection can't hold null values");

		this.permissions.clear();
		this.permissions.addAll(permissions);
	}

	public boolean isAccountNonExpired() {
		// out of scope of this project
		return true;
	}

	public boolean isAccountNonLocked() {
		// out of scope of this project
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// out of scope of this project
		return true;
	}

	public boolean isEnabled() {
		// out of scope of this project
		return true;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableSet(permissions);
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
		final User user = (User) other;
		return Objects.equals(getUsername(), user.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getUsername());
	}
}
