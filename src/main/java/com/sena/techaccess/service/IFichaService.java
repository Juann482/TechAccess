package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Ficha;

public interface IFichaService {

	// CRUD
	Ficha save(Ficha ficha); // Crear o actualizar

	void delete(Integer idFicha); // Eliminar

	//Ficha findByNumficha(Integer numFicha);
	
	Ficha findByNombrePrograma(String nombrePrograma);
	
	void update(Ficha ficha);

	Optional<Ficha> get(Integer idFicha);
	
	List<Ficha> findAll(); // Listar todas las fichas

}
