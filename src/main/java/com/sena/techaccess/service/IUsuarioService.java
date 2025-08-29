package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import com.sena.techaccess.model.Usuario;

public interface IUsuarioService {

	public Optional<Usuario> findById(Integer id);

	public Usuario save(Usuario usuario);

	public void deleteById(Integer id);

	public Usuario findByUsername(String username);

	public Usuario findByEmail(String email);

	List<Usuario> findAll();
}
