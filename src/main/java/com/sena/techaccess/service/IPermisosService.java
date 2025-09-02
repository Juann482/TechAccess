package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Permisos;
import com.sena.techaccess.model.Usuario;

public interface IPermisosService {

	// CRUD básico
	Permisos save(Permisos permiso); // Crear o actualizar

	void delete(Integer id); // Eliminar

	Optional<Permisos> findById(Integer id); // Buscar por ID

	List<Permisos> findAll(); // Listar todos los permisos

	// Consultas personalizadas
	List<Permisos> findByTipoPermiso(String tipoPermiso);

	List<Permisos> findByRutaPermiso(String rutaPermiso);

	List<Permisos> findByUsuario(Usuario usuario);
}
