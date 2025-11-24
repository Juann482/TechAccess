package com.sena.techaccess.repository;

import com.sena.techaccess.model.SuperAdministrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperAdministradorRepository extends JpaRepository<SuperAdministrador, Integer> {

	Optional<SuperAdministrador> findByEmail(String email);

	Optional<SuperAdministrador> findByDocumento(String documento);

	List<SuperAdministrador> findByEstado(String estado);

	@Query("SELECT s FROM SuperAdministrador s WHERE s.nombre LIKE %:nombre%")
	List<SuperAdministrador> findByNombreContaining(@Param("nombre") String nombre);

	boolean existsByEmail(String email);

	boolean existsByDocumento(String documento);
}