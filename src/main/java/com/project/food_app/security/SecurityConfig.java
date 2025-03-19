package com.project.food_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	JWTTokenFilter jwtTokenFilter;
	@Autowired
	JWTUtil jwtUtil;
	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		System.out.println("Initilizing Bean AuthenticationManager");
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("securityFilterChain running");
		http.csrf(csrf -> csrf.disable())
		        .cors(cors -> cors.disable())
				.authorizeHttpRequests(
					auth -> auth.requestMatchers("/login", "/register")
												.permitAll()
												.anyRequest()
												.authenticated())
				.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
				
		
		return http.build();
	}
}
