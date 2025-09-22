package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import com.sena.techaccess.model.Soporte;

public interface ISoporteService {

	public Optional<Soporte> findById(Integer id);

	public Soporte save(Soporte soporte);										

	public void delete(Integer id);

	public Soporte findByNombre(String nombre);

	public Soporte findByEmail(String email);

	List<Soporte> findAll();

}
