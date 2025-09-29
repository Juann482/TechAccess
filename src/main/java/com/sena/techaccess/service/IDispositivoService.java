package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.Usuario;

public interface IDispositivoService {
	
	Optional<Dispositivo> findById(Integer id);
	
	Dispositivo findByTipoD(String tipoD);
	
	List<Dispositivo> findByUsuario(Usuario usuario);
	
	Dispositivo findByMarca(String marca);
	
	Optional<Dispositivo> findByColor(String color);
	
	void update (Dispositivo dispositivo);
	
	void delete (Integer id);
	
	Dispositivo save (Dispositivo dispositivo);
	
	List<Dispositivo> findAll();

}
