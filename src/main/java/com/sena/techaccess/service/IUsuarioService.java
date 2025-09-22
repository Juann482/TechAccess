package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.dto.UsuarioDTO;
import com.sena.techaccess.model.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Integer id);

	public Usuario save(Usuario usuario);

	public void deleteById(Integer id);

	public Usuario findByNombre(String nombre);

	public Usuario findByEmail(String email);

	public Optional<Usuario> findByDocumento(String documento);
	
	List<Usuario> findAll();
	
	List<UsuarioDTO> listaUsuarios();
}
