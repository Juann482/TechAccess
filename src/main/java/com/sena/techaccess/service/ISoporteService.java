package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Soporte;

public interface ISoporteService {

	public Optional<Soporte> get(Integer id);

	public Soporte save(Soporte soporte);

	public Soporte findByConsultante(String consultante);

	public Soporte findByEmail(String email);
	
	List<Soporte> findAll();

}
