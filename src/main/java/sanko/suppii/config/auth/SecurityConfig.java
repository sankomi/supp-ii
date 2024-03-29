package sanko.suppii.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

import sanko.suppii.config.auth.CustomOAuth2UserService;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/").permitAll()
			.requestMatchers("/api/v1/**").permitAll()
			.requestMatchers("/emails/**", "/user/**").hasAnyRole("GUEST", "USER")
			.anyRequest().denyAll()
			.and().logout().logoutSuccessUrl("/")
			.and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
		return http.build();
	}

}
