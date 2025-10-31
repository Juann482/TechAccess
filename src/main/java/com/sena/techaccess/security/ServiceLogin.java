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
	
	private final Logger LOGGER =  LoggerFactory.getLogger(ServiceLogin.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> user = usuarioService.findByEmail(username);
		LOGGER.warn("Este es el user ingresando");
		if (user.isPresent()) {
			
			session.setAttribute("IdUser", user.get().getId());
			Usuario present = user.get();
			return User.builder()
					.username(present.getEmail())
					.password(present.getPassword())
					.roles(present.getRol())
					.build();
		} else {
			throw new UsernameNotFoundException("Usuarios no encontrado");
		}
	}

	public String encodePass(String rowPass) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		return pe.encode(rowPass);
	}
}
