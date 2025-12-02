	package com.sena.techaccess.service;

import java.util.List;

import java.util.Optional;

/*import java.util.stream.Collectors;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// >>>>>>>>>>>>>>>> DASHBOARD <<<<<<<<<<<<<<<<<

	@Override
	public int totalUsuarios() {
		return (int) usuarioRepository.count();
	}

	@Override
	public int Inactivos() {
		return (int) usuarioRepository.countByEstadoCuenta("Inactivo");
	}

	@Override
	public int Activos() {
		return (int) usuarioRepository.countByEstadoCuenta("Activo");
	}

	@Override
	public int Aprendiz() {
		return (int) usuarioRepository.countByRol("Aprendiz");
	}

	@Override
	public int Instructor() {
		return (int) usuarioRepository.countByRol("Instructor");
	}

	@Override
	public int Visitante() {
		return (int) usuarioRepository.countByRol("Visitantes");
	}

//>>>>>>>>>>>>>>>>>>>>>> FIN DASHBOARD <<<<<<<<<<<<<<<<<<<<<

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> get(Integer id) {
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
	public Usuario findByNombre(String nombre) {
		return usuarioRepository.findByNombre(nombre);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findByEstadoCuenta(String estadoCuenta) {
		return usuarioRepository.findByEstadoCuenta(estadoCuenta);
	}

	@Override
	public List<Usuario> findByRol(String rol) {
		return usuarioRepository.findByRol(rol);
	}

	@Override
	public Usuario findByDocumento(String documento) {
		return usuarioRepository.findByDocumento(documento);
	}

	@Override
	public Page<Usuario> filtrarUsuarios(String nombre, String documento, String rol, String estado, Pageable pageable) {
		return usuarioRepository.filtrarUsuarios(nombre, documento, rol, estado, pageable);
	}

	@Override
	public Page<Usuario> findByFichaId(Integer fichaId, Pageable pageable) {
		return usuarioRepository.findByFichaId(fichaId, pageable);
	}

	@Override
	public void saveAll(List<Usuario> usuarios) {
		usuarioRepository.saveAll(usuarios);		
	}

	@Override
	public Page<Usuario> filtrarUsuariosEnFicha(Integer fichaId, String nombre, String documento, String rol,
	        String estado, Pageable pageable) {
	    return usuarioRepository.filtrarUsuariosEnFicha(fichaId, nombre, documento, rol, estado, pageable);
	}

	@Override
	public List<Usuario> findByFichaId(Integer id) {
		return usuarioRepository.findByFichaId(id);
	}
}
