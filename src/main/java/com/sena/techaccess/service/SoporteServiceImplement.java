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
	public Optional<Soporte> findById(Integer id) {
		// TODO Auto-generated method stub
		return soporteRepository.findById(id);
	}

	@Override
	public Soporte save(Soporte soporte) {
		// TODO Auto-generated method stub
		return soporteRepository.save(soporte);
	}

	@Override
	public Soporte findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return soporteRepository.findByNombre(nombre);
	}

	@Override
	public Soporte findByEmail(String email) {
		// TODO Auto-generated method stub
		return soporteRepository.findByEmail(email);
	}

	@Override
	public List<Soporte> findAll() {
		// TODO Auto-generated method stub
		return soporteRepository.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
