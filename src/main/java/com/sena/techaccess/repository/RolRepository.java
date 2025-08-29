package com.sena.techaccess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sena.techaccess.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

	Optional<Rol> findByTipo(String tipo);
}
