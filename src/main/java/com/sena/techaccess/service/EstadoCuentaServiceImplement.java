package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.EstadoCuenta;
import com.sena.techaccess.repository.EstadoCuentaRepository;

@Service
public class EstadoCuentaServiceImplement implements IEstadoCuentaService{
	
	@Autowired
	private EstadoCuentaRepository estadoCuentaRepository;

	@Override
	public Optional<EstadoCuenta> findById(Integer idEstado) {
		// TODO Auto-generated method stub
		return estadoCuentaRepository.findById(idEstado);
	}

	@Override
	public List<EstadoCuenta> findAll() {
		// TODO Auto-generated method stub
		return estadoCuentaRepository.findAll();
	}

	
}
