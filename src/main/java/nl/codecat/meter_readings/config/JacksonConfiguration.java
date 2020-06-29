package nl.codecat.meter_readings.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

	@Bean
	public Module jdk8Module() {
		return new Jdk8Module();
	}

	@Bean
	public Module javaTimeModule() {
		return new JavaTimeModule();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> builder.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
	}
}
