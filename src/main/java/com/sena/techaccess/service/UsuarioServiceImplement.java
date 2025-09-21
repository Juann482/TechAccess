package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.UsuarioRepository;

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

	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

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
}
