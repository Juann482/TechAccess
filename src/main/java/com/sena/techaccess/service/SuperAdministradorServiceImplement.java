package com.sena.techaccess.service;

import com.sena.techaccess.model.SuperAdministrador;
import com.sena.techaccess.repository.SuperAdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperAdministradorServiceImplement {

	@Autowired
	private SuperAdministradorRepository superAdministradorRepository;

	public List<SuperAdministrador> findAll() {
		return superAdministradorRepository.findAll();
	}

	public Optional<SuperAdministrador> findById(Integer id) {
		return superAdministradorRepository.findById(id);
	}

	public Optional<SuperAdministrador> findByEmail(String email) {
		return superAdministradorRepository.findByEmail(email);
	}

	public SuperAdministrador save(SuperAdministrador superAdministrador) {
		return superAdministradorRepository.save(superAdministrador);
	}

	public void deleteById(Integer id) {
		superAdministradorRepository.deleteById(id);
	}

	public boolean existsByEmail(String email) {
		return superAdministradorRepository.existsByEmail(email);
	}

	public boolean existsByDocumento(String documento) {
		return superAdministradorRepository.existsByDocumento(documento);
	}

	public List<SuperAdministrador> findByEstado(String estado) {
		return superAdministradorRepository.findByEstado(estado);
	}

	public List<SuperAdministrador> findByNombreContaining(String nombre) {
		return superAdministradorRepository.findByNombreContaining(nombre);
	}
}