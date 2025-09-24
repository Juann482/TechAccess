package com.sena.techaccess.service;

import java.util.List;

import java.util.Optional;

/*import java.util.stream.Collectors;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

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

	@Override
	public Optional<Usuario> get(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);

	}

	@Override
	public void update(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	// @Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void delete(Integer id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public void update(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Integer id) {
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

	@Override
	public Usuario obtenerUsuario() {
		// TODO Auto-generated method stub
		return null;
	}


	// metodito bn belico para validar por nombre o email
	public Usuario validarUsuario(String userInput, String password) {
		// Buscar por correo de usuario
		Usuario user = findByEmail(userInput);

		// Validar contarseña
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}

		return null;
	}

}
