package sanko.suppii.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> {
			authorize
				.requestMatchers("/").permitAll()
				.requestMatchers("/emails/", "/emails/**").hasRole("USER")
				.anyRequest().denyAll();
		})
			.oauth2Login();
		return http.build();
	}

}
