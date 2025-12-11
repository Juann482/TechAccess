package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;

public interface IFichaService {

	// CRUD
	Ficha save(Ficha ficha); // Crear o actualizar

	void delete(Integer id); // Eliminar

	List<Ficha> findByEstado(String estado);
	
	Ficha findByNombrePrograma(String nombrePrograma);
	
	Ficha findByJornada(String jornada);
	
	void update(Ficha ficha);

	Optional<Ficha> get(Integer id);
	
	List<Ficha> findAll(); // Listar todas las fichas
	
	Page<Ficha> filtrarFicha(String nombrePrograma, String jornada, String estado, Integer numFicha, Pageable pageable);


}
