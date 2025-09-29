package com.sena.techaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.EstadoCuenta;
import com.sena.techaccess.model.Rol;
import com.sena.techaccess.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByNombre(String nombres);

	List<Usuario> findByEstadoCuenta(EstadoCuenta nombreEstado);

	List<Usuario> findByRol(Rol rol);

	// ================================================0

}