package com.sena.techaccess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Soporte;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer> {
	
	Soporte findByEmail(String email);

	Optional<Soporte> findByMensaje(String mensaje);

	Soporte findByNombre(String nombre);

}
