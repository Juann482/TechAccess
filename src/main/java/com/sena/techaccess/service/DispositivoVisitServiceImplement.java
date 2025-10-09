package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.repository.DispositivoVisitRepository;

@Service
public class DispositivoVisitServiceImplement implements IDispositivoVisitService{
	
	@Autowired
	private DispositivoVisitRepository dispositivoVisitRepository;

	@Override
	public Optional<DispositivoVisit> get(Integer id) {
		return dispositivoVisitRepository.findById(id);
	}

	@Override
	public DispositivoVisit findByTipo(String tipo) {
		return dispositivoVisitRepository.findByTipo(tipo);
	}

	@Override
	public DispositivoVisit findByMarca(String marca) {
		return dispositivoVisitRepository.findByMarca(marca);
	}

	@Override
	public DispositivoVisit findByColor(String color) {
		return dispositivoVisitRepository.findByColor(color);
	}

	@Override
	public DispositivoVisit save(DispositivoVisit dispositivoVisit) {
		return dispositivoVisitRepository.save(dispositivoVisit);
	}

	@Override
	public void delete(Integer id) {
		dispositivoVisitRepository.deleteById(id);
	}

	@Override
	public void update(DispositivoVisit dispositivoVisit) {
		dispositivoVisitRepository.save(dispositivoVisit);
	}

	@Override
	public List<DispositivoVisit> findAll() {
		return dispositivoVisitRepository.findAll();
	}

}
