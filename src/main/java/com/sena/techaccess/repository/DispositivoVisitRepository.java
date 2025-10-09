package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.DispositivoVisit;

@Repository
public interface DispositivoVisitRepository extends JpaRepository<DispositivoVisit, Integer>{

	DispositivoVisit findByColor(String color);

	DispositivoVisit findByMarca(String marca);

	DispositivoVisit findByTipo(String tipo);

}
