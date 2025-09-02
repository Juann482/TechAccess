package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;

public interface IFichaService {

	// CRUD
	Ficha save(Ficha ficha); // Crear o actualizar

	void delete(Integer idFicha); // Eliminar

	Optional<Ficha> findById(Integer idFicha); // Buscar por ID

	List<Ficha> findAll(); // Listar todas las fichas

	// Consultas personalizadas
	List<Ficha> findByNombrePrograma(String nombrePrograma);

	Ficha findByNuumFicha(String nuumFicha);

	List<Usuario> findUsuariosByIdFicha(Integer idFicha);
}
