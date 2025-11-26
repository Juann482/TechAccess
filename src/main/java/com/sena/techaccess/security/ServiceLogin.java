package com.sena.techaccess.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiceLogin implements UserDetailsService {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private HttpSession session;

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceLogin.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioService.findByEmail(username);
        LOGGER.info("Intentando cargar usuario: {}", username);

        if (user.isPresent()) {
            Usuario present = user.get();
            
            session.setAttribute("IdUser", present.getId());
            session.setAttribute("nombre", present.getNombre());
            session.setAttribute("email", present.getEmail());
            session.setAttribute("rol", present.getRol());

            return User.builder()
                    .username(present.getEmail())
                    .password(present.getPassword())
                    .authorities(present.getRol()) // usamos el rol tal cual en la DB
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    // Método útil para encriptar contraseñas
    public String encodePass(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }
}
