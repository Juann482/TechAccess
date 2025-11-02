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
        	
        	String rol = authority.getAuthority();
        	System.out.println("============= Tomando roles =========");
        	switch(rol) {
        	
        	case "ROLE_Administrador":
        		
        		response.sendRedirect("Administrador/usuarios");
        		return;
        	
        	case "ROLE_Vigilancia":
        		
        		response.sendRedirect("Vigilancia/Ingreso");
        		return;
        		
        	default: 
        		
        		response.sendRedirect("/");
        	}
        	
            
        }

        // Si no tiene rol reconocido
        response.sendRedirect("/");
    }
}
