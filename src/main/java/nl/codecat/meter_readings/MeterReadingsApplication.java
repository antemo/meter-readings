package nl.codecat.meter_readings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class MeterReadingsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MeterReadingsApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	public static class AuditorAwareImpl implements AuditorAware<String> {

		public Optional<String> getCurrentAuditor() {
			return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
		}
	}
}
