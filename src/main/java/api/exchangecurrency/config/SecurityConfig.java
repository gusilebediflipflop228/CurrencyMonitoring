package api.exchangecurrency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Placeholder configuration. Actual web security configuration is located in
 * api.exchangecurrency.security.SecurityConfig.
 * @removed @EnableWebSecurity to avoid duplicate web security configuration
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
}

