package com.sena.techaccess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.model.Usuario;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer> {

	Optional<Usuario> findByEmail(String email);

	Optional<Soporte> findByMensaje(String mensaje);
}
