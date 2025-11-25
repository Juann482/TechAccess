package com.sena.techaccess.repository;

import com.sena.techaccess.model.Libro;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    @Query("SELECT l FROM Libro l WHERE " +
           "(:busqueda IS NULL OR UPPER(l.titulo) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR " +
           "UPPER(l.autor) LIKE UPPER(CONCAT('%', :busqueda, '%'))) AND " +
           "(:estado IS NULL OR l.estado = :estado)")
    Page<Libro> buscarConFiltros(
        @Param("busqueda") String busqueda,
        @Param("estado") Libro.EstadoLibro estado,
        Pageable pageable
    );

    @Modifying
    @Query("UPDATE Libro l SET l.estado = CASE WHEN l.estado = 'DISPONIBLE' THEN 'PRESTADO' ELSE 'DISPONIBLE' END WHERE l.id = :id")
    void cambiarEstado(@Param("id") Integer id);
}