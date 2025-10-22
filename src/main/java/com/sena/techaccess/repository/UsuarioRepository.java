package com.sena.techaccess.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	Usuario findByNombre(String nombres);

	List<Usuario> findByRol(String rol);

	List<Usuario> findByEstadoCuenta(String estadoCuenta);

	Usuario findByDocumento(String documento);

	// ================================================0

}