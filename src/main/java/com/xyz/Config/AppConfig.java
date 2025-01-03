package com.xyz.Config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll()).addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf().disable().cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest    request) {
				// TODO Auto-generated method stub
		        CorsConfiguration cfg = new CorsConfiguration();
		        cfg.setAllowedOrigins(java.util.Arrays.asList("http://localhost:3000/"));
		        
		        cfg.setAllowedMethods(Collections.singletonList("*"));
		        cfg.setAllowedHeaders(Collections.singletonList("*"));
		        cfg.setExposedHeaders(java.util.Arrays.asList("Authorization"));
		        cfg.setMaxAge(36000000L);
				return cfg;
			}
		}).and().httpBasic().and().formLogin();
		
		
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
