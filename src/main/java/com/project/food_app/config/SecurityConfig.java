package com.project.food_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/register").permitAll() // Allow
//																										// login/register
//						.anyRequest().authenticated())
//				// Disable CSRF if necessary
//				.formLogin(login -> login.loginProcessingUrl("/login")
//						.successHandler(
//								(request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
//						.failureHandler((request, response, exception) -> response
//								.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
//						.permitAll())

//				.logout(logout -> logout.logoutUrl("/logout")
//						.logoutSuccessHandler(
//								(request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))// Define
//
//						.logoutSuccessUrl("/") // Redirect to home after logout
//						.invalidateHttpSession(true).deleteCookies("JSESSIONID"))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

		return http.build();
	}
}
