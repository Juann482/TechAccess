package com.sena.techaccess.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	Usuario findByNombre(String nombres);

	List<Usuario> findByRol(String rol);

	List<Usuario> findByEstadoCuenta(String estadoCuenta);

	Usuario findByDocumento(String documento);

	//En funcion con la base de datos
	@Query("""
		       SELECT u FROM Usuario u
		       WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
		       AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
		       AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
		       AND (:estado IS NULL OR :estado = '' OR u.estadoCuenta = :estado)
		       """)
		List<Usuario> filtrarUsuarios(
		        @Param("nombre") String nombre,
		        @Param("documento") String documento,
		        @Param("rol") String rol,
		        @Param("estado") String estado
		);


	// ================================================0

}