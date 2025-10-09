package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.DispositivoVisit;

public interface IDispositivoVisitService {
	
	Optional<DispositivoVisit> get(Integer id);

	DispositivoVisit findByTipo(String tipo);
	
	DispositivoVisit findByMarca(String marca);
	
	DispositivoVisit findByColor(String color);
	
	DispositivoVisit save(DispositivoVisit dispositivoVisit);
	
	void delete(Integer id);
	
	void update(DispositivoVisit dispositivoVisit);
	
	List<DispositivoVisit> findAll();
}
