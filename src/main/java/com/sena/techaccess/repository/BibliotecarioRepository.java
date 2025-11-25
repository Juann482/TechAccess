package com.sena.techaccess.repository;

import com.sena.techaccess.model.Bibliotecario;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Integer> {

	@Query("SELECT b FROM Bibliotecario b WHERE "
			+ "(:busqueda IS NULL OR UPPER(b.nombre) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR "
			+ "UPPER(b.email) LIKE UPPER(CONCAT('%', :busqueda, '%'))) "
			+ "AND (:estado IS NULL OR b.estado = :estado)")
	Page<Bibliotecario> buscarConFiltros(@Param("busqueda") String busqueda, @Param("estado") String estado,
			Pageable pageable);

	@Modifying
	@Query("UPDATE Bibliotecario b SET b.estado = CASE WHEN b.estado = com.sena.techaccess.model.Bibliotecario.Estado.ACTIVO "
			+ "THEN com.sena.techaccess.model.Bibliotecario.Estado.INACTIVO "
			+ "ELSE com.sena.techaccess.model.Bibliotecario.Estado.ACTIVO END " + "WHERE b.id = :id")
	@Transactional
	void cambiarEstado(@Param("id") Integer id);
}