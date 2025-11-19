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
            System.out.println("============= Rol detectado: " + rol + " =========");

            switch (rol) {
                case "Administrador":
                    response.sendRedirect("/Administrador/usuarios");
                    System.out.println("Usuario logueado como admin");
                    return;

                case "Vigilancia":
                    response.sendRedirect("/Vigilancia/Ingreso");
                    System.out.println("Usuario logueado como vigilancia");
                    return;
                    
                case "Aprendiz":
                    response.sendRedirect("/Aprendiz/aprendiz");
                    System.out.println("Usuario logueado como aprendiz");
                    return;
                    
                case "Instructor":
                    response.sendRedirect("/instructor/Inicio");
                    System.out.println("Usuario logueado como instructor");
                    return;

                default:
                    response.sendRedirect("/");
                    System.out.println("Usuario sin rol válido");
                    return;
            }
        }

        // Fallback si no hay roles
        response.sendRedirect("/");
    }
}
