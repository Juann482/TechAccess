package com.sena.techaccess.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private UserDetailsService userDetailsService;

	private CustomSuccessHandler successHandler;

	public SecurityConfig(CustomSuccessHandler successHandler, UserDetailsService userDetailsService) {
		this.successHandler = successHandler;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		      .csrf(csrf -> csrf.disable())
		      
		      .authorizeHttpRequests
		      
		      (Primario -> Primario
		    		  .requestMatchers("/","/assets/**", "/images/**", "/css/**", "/js/**", "/img/**").permitAll()
		    		  .requestMatchers("/Administrador/**").hasRole("Administrador")
		    		  .requestMatchers("/Vigilancia/**").hasRole("Vigilancia")
		    		  .requestMatchers("/instructor/**").hasRole("Instructor")
		    		  .requestMatchers("/Aprendiz/**").hasRole("Aprendiz")
		    		  .anyRequest().authenticated())
		    		  
		.formLogin(Login -> Login
				.loginPage("/")
				.loginProcessingUrl("/login")
                .successHandler(successHandler)
				.permitAll())
		
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll())
		
		.userDetailsService(userDetailsService);
	            

				return http.build();
		
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Para Autenticacion de usuarios
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
