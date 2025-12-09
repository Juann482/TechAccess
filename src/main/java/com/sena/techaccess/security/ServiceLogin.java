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
			session.setAttribute("documento", present.getDocumento());
			session.setAttribute("email", present.getEmail());
			session.setAttribute("telefono", present.getTelefono());
			session.setAttribute("direccion", present.getDireccion());
			session.setAttribute("ficha", present.getFicha());
			session.setAttribute("rol", present.getRol());

			// Guardar el objeto completo
			session.setAttribute("usuarioSesion", present);

			boolean isInactivo;

			// Manejo null seguro para estadoCuenta
			if (present.getEstadoCuenta() != null) {
				isInactivo = !present.getEstadoCuenta().equalsIgnoreCase("Activo");
			} else {
				// Si es null, asumimos que está inactivo por seguridad
				LOGGER.warn("Usuario {} tiene estadoCuenta NULL. Marcando como inactivo.", present.getEmail());
				isInactivo = true;
			}

			return User.builder().username(present.getEmail()).password(present.getPassword())
					.authorities(present.getRol()) // usamos el rol tal cual en la DB
					.disabled(isInactivo).build();
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
