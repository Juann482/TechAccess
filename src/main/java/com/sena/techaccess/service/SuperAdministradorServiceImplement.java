package com.sena.techaccess.service;

import com.sena.techaccess.model.SuperAdministrador;
import com.sena.techaccess.repository.SuperAdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuperAdministradorServiceImplement implements ISuperadminService {

	@Autowired
	private SuperAdministradorRepository superAdministradorRepository;

	@Override
	public List<SuperAdministrador> findAll() {
		return superAdministradorRepository.findAll();
	}

	@Override
	public Optional<SuperAdministrador> findById(Integer id) {
		return superAdministradorRepository.findById(id);
	}

	@Override
	public Optional<SuperAdministrador> findByEmail(String email) {
		return superAdministradorRepository.findByEmail(email);
	}

	@Override
	public SuperAdministrador save(SuperAdministrador superAdministrador) {
		return superAdministradorRepository.save(superAdministrador);
	}

	@Override
	public void deleteById(Integer id) {
		superAdministradorRepository.deleteById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return superAdministradorRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByDocumento(String documento) {
		return superAdministradorRepository.existsByDocumento(documento);
	}

	@Override
	public List<SuperAdministrador> findByEstado(String estado) {
		return superAdministradorRepository.findByEstado(estado);
	}

	@Override
	public List<SuperAdministrador> findByNombreContaining(String nombre) {
		return superAdministradorRepository.findByNombreContaining(nombre);
	}
}