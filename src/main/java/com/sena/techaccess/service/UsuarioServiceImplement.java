package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.UsuarioRepository;
import com.sena.techaccess.dto.UsuarioDTO;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

<<<<<<< HEAD
=======
	@Override
>>>>>>> 37c1ca7ec7973725c95d88823b7af7f8346f14e3
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
<<<<<<< HEAD

	@Transactional
	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username).orElse(null);
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email).orElse(null);
	}

	// metodito bn belico para validar por nombre o email
	public Usuario validarUsuario(String userInput, String password) {
		// Buscar por nombre de usuario
		Usuario user = findByUsername(userInput);

		// Si no buqueda por email
		if (user == null) {
			user = findByEmail(userInput);
		}

		// y Validar contarseña
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}

		return null;
	}
=======

	@Override
	@Transactional
	public void deleteById(Integer id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario findByNombre(String nombre) {
		throw new UnsupportedOperationException("Método findByNombre aún no implementado");
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email).orElse(null);
	}

	@Override
	public Optional<Usuario> findByDocumento(String documento) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	// =========================================================================

	@Override
	public List<UsuarioDTO> listaUsuarios() {
		return usuarioRepository.findAll()
				.stream()
				.map(usuario -> convertirADTO(usuario))
				.collect(Collectors.toList());

	}

	private UsuarioDTO convertirADTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getNombre(), usuario.getEmail(), usuario.getDocumento(), usuario.getTelefono(),
				usuario.getRol() != null ? usuario.getRol().getTipo() : "Sin rol",
				usuario.getNFicha() != null ? usuario.getNFicha().toString() : "Sin ficha",
				usuario.getEstadoCuenta() != null ? usuario.getEstadoCuenta().getNombreEstado() : "Sin estado");
	}

>>>>>>> 37c1ca7ec7973725c95d88823b7af7f8346f14e3
}
