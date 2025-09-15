package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import com.sena.techaccess.model.Rol;

public interface IRolService {

	
	void rolesEspecificos();
		
	public Optional<Rol> findById(Integer id);

	public Rol save(Rol rol);

	public void deleteById(Integer id);

	List<Rol> findAll();

}
