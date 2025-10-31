package com.sena.techaccess.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority authority : authorities) {

			String role = authority.getAuthority();

			switch (role) {

			case "ROLE_Administrador":
				response.sendRedirect("/Administrador/usuarios");
				return;
			case "ROLE_Vigilancia":
				response.sendRedirect("/Vigilancia/*");
				return;
			case "ROLE_Aprendiz":
				response.sendRedirect("/Aprendiz/*");
				return;
			case "ROLE_Instructor":
				response.sendRedirect("/instructor/*");
				return;
			default:
				response.sendRedirect("/");
				return;
			}

		}
		response.sendRedirect("/");
	}

}
