package com.sena.techaccess.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final ServiceLogin serviceLogin;
	private final CustomSuccessHandler successHandler;

	public SecurityConfig(ServiceLogin serviceLogin, CustomSuccessHandler successHandler) {
		this.serviceLogin = serviceLogin;
		this.successHandler = successHandler;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/Home", "/assets/**", "/images/**", "/css/**", "/js/**", "/img/**", "/error/**",
								"/soporte", "/about")
						.permitAll()
						.requestMatchers("/Administrador/**").hasAuthority("Administrador")
						.requestMatchers("/Vigilancia/**").hasAuthority("Vigilancia")
						.requestMatchers("/Aprendiz/**").hasAuthority("Aprendiz")
						.requestMatchers("/instructor/**").hasAuthority("Instructor")
						.requestMatchers("/funcionario/**").hasAuthority("Funcionario")
						.anyRequest().authenticated())
				
				.formLogin(login -> login
						.loginPage("/Home")
						.loginProcessingUrl("/login")
						.successHandler(successHandler)
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll())
				.userDetailsService(serviceLogin)
				// 👉 Corrección agregada (ENVÍA AL LOGIN EN VEZ DE 403)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/Home")));

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
