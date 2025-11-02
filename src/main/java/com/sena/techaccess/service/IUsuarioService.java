package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> get(Integer id);

	public Usuario save(Usuario usuario);
	
	List<Usuario> findByEstadoCuenta(String estadoCuenta);

	public void delete(Integer id);

	public void update(Usuario usuario);

	public Usuario findByNombre(String nombre);

	public Optional<Usuario> findByEmail(String email);
	
	public List<Usuario> findByRol(String rol);

	public Usuario findByDocumento(String documento);

	List<Usuario> findAll();

}