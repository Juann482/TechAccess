package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Rol;
import com.sena.techaccess.repository.RolRepository;

@Service
public class RolServiceImplement implements IRolService {

	@Autowired
	private RolRepository rolRepository;


	@Override
	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	@Override
	public Optional<Rol> findById(Integer id) {
		return rolRepository.findById(id);
	}

	@Override
	public Rol save(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	public void deleteById(Integer id) {
		rolRepository.deleteById(id);
	}

	@Override
	public void rolesEspecificos() {
		// TODO Auto-generated method stub
		
	}
}
