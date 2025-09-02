package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Permisos;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.PermisosRepository;

@Service
public class PermisosServiceImplement implements IPermisosService {

	@Autowired
	private PermisosRepository permisosRepository;

	public Permisos save(Permisos permiso) {
		return permisosRepository.save(permiso);
	}

	public void delete(Integer id) {
		permisosRepository.deleteById(id);
	}

	public Optional<Permisos> findById(Integer id) {
		return permisosRepository.findById(id);
	}

	public List<Permisos> findAll() {
		return permisosRepository.findAll();
	}

	public List<Permisos> findByTipoPermiso(String tipoPermiso) {
		return permisosRepository.findByTipoPermiso(tipoPermiso);
	}

	public List<Permisos> findByRutaPermiso(String rutaPermiso) {
		return permisosRepository.findByRutaPermiso(rutaPermiso);
	}

	public List<Permisos> findByUsuario(Usuario usuario) {
		return permisosRepository.findByUsuario(usuario);
	}
}
