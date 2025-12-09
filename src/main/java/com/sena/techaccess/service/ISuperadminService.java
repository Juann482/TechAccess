package com.sena.techaccess.service;

import com.sena.techaccess.model.SuperAdministrador;
import java.util.List;
import java.util.Optional;

public interface ISuperadminService {
	List<SuperAdministrador> findAll();

	Optional<SuperAdministrador> findById(Integer id);

	Optional<SuperAdministrador> findByEmail(String email);

	SuperAdministrador save(SuperAdministrador superAdministrador);

	void deleteById(Integer id);

	boolean existsByEmail(String email);

	boolean existsByDocumento(String documento);

	List<SuperAdministrador> findByEstado(String estado);

	List<SuperAdministrador> findByNombreContaining(String nombre);
}