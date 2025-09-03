package com.sena.techaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.EstadoPermanencia;

@Repository
public interface EstadoPermanenciaRepository extends JpaRepository<EstadoPermanencia, Integer>{
	
	public List<EstadoPermanencia>findById(EstadoPermanencia idEstadopermanencia);
	
	public List<EstadoPermanencia>findBynomEstadoPermanencias(EstadoPermanencia nomEstadoPermanencia);

}