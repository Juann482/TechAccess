package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.repository.SoporteRepository;

@Service
public class SoporteServiceImplement implements ISoporteService {

	@Autowired
	private SoporteRepository soporteRepository;

	@Override
	public Optional<Soporte> get(Integer id) {
		return soporteRepository.findById(id);
	}

	@Override
	public Soporte save(Soporte soporte) {
		return soporteRepository.save(soporte);
	}

	@Override
	public Soporte findByConsultante(String consultante) {
		return soporteRepository.findByConsultante(consultante);
	}

	@Override
	public Soporte findByEmail(String email) {
		return soporteRepository.findByEmail(email);
	}

	@Override
	public List<Soporte> findAll() {
		return soporteRepository.findAll();
	}

}
