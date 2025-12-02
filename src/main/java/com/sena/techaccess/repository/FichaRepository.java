package com.sena.techaccess.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sena.techaccess.model.Ficha;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {   
	
	// Buscar fichas por nombre del programa
    Ficha findByNombrePrograma(String nombrePrograma);

	Ficha findByJornada(String jornada);

	List<Ficha> findByEstado(String estado);

	@Query("""
			SELECT f FROM Ficha f
			WHERE  (:nombrePrograma IS NULL OR :nombrePrograma = '' OR LOWER(f.nombrePrograma) LIKE LOWER(CONCAT('%', :nombrePrograma, '%')))
			AND  (:jornada IS NULL OR :jornada = '' OR LOWER(f.jornada) LIKE LOWER(CONCAT('%', :jornada, '%')))
			AND (:estado IS NULL OR :estado = '' OR f.estado = :estado)
			AND (:numFicha IS NULL OR f.numFicha = :numFicha)
			""")
	Page<Ficha> filtrarficha(@Param("nombrePrograma") String nombrePrograma,
			                 @Param("jornada") String jornada,
			                 @Param("estado") String estado,
			                 @Param("numFicha") Integer numFicha,
			                 Pageable pageable);
    
}