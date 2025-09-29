package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.DispositivoRepository;

@Service
public class DispositivoServiceImplement implements IDispositivoService {
	
	@Autowired
	private DispositivoRepository dispositivoRepository;

	@Override
	public Optional<Dispositivo> findById(Integer id) {
		return dispositivoRepository.findById(id);
	}

	@Override
	public Dispositivo findByTipoD(String tipoD) {
		return dispositivoRepository.findByTipoD(tipoD);
	}

	@Override
	public List<Dispositivo> findByUsuario(Usuario usuario) {
		return dispositivoRepository.findByUsuario(usuario);
	}

	@Override
	public Dispositivo findByMarca(String marca) {
		return dispositivoRepository.findByMarca(marca);
	}

	@Override
	public Optional<Dispositivo> findByColor(String color) {
		return dispositivoRepository.findByColor(color);
	}

	@Override
	public void update(Dispositivo dispositivo) {
		dispositivoRepository.save(dispositivo);		
	}

	@Override
	public void delete(Integer id) {
		dispositivoRepository.deleteById(id);		
	}

	@Override
	public Dispositivo save(Dispositivo dispositivo) {
		return dispositivoRepository.save(dispositivo);
	}

	@Override
	public List<Dispositivo> findAll() {
		return dispositivoRepository.findAll();
	}

}
