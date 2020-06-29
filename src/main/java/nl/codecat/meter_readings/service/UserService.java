package nl.codecat.meter_readings.service;

import nl.codecat.meter_readings.entity.security.User;
import nl.codecat.meter_readings.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
@Primary
@Transactional
public class UserService extends AbstractService<User, UUID> implements UserDetailsService {

	private final UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
	}

	@Override
	public User create(final User user) {
		Assert.notNull(user, "User to create can't be null");

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	public void updatePassword(final String oldPassword, final String newPassword) {
		Assert.hasText(oldPassword, "Old password to verify can't be null or empty");
		Assert.hasText(newPassword, "New password to set can't be null or empty");

		final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new BadCredentialsException("Provided password doesn't match current password");
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);
	}

	/**
	 * This is added through setter to avoid cyclic dependency issues
	 */
	@Autowired
	public void setPasswordEncoder(final BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected JpaRepository<User, UUID> getRepository() {
		return userRepository;
	}
}