package com.sena.techaccess.service;

import java.util.Optional;

import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.model.Usuario;

public interface ISoporteService {

	public Optional<Soporte> findById(Integer id);

	public Soporte save(Usuario usuario);

	public void deleteById(Integer id);

	public Usuario findByNombre(String nombre);

	public Usuario findByEmail(String email);

	public Object findAll();

}
