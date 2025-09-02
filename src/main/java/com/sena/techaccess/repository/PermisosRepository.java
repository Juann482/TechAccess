package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sena.techaccess.model.Permisos;
import com.sena.techaccess.model.Usuario;
import java.util.List;

@Repository
public interface PermisosRepository extends JpaRepository<Permisos, Integer> {

	// Buscar permisos por tipo
	List<Permisos> findByTipoPermiso(String tipoPermiso);

	// Buscar permisos por ruta
	List<Permisos> findByRutaPermiso(String rutaPermiso);

	// Buscar permisos de un usuario
	List<Permisos> findByUsuario(Usuario usuario);
}