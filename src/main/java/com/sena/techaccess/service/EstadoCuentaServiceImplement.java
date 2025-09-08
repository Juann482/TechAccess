package com.sena.techaccess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.EstadoCuenta;
import com.sena.techaccess.repository.EstadoCuentaRepository;

@Service
public class EstadoCuentaServiceImplement implements IEstadoCuentaService {

	@Autowired
	private EstadoCuentaRepository estadoCuentaRepository;

	@Override
	public void inicializarEstados(){
		
		EstadoCuenta activo = new EstadoCuenta();
		activo.setidEstado(EstadoCuenta.U_Activo);
		activo.setNombreEstado(EstadoCuenta.NomActivo);
		estadoCuentaRepository.save(activo);
		
		EstadoCuenta inactivo = new EstadoCuenta();
		inactivo.setidEstado(EstadoCuenta.U_Inactivo);
		inactivo.setNombreEstado(EstadoCuenta.NomInactivo);
		estadoCuentaRepository.save(inactivo);
	
	}
		
	@Override
	public EstadoCuenta save(EstadoCuenta estadocuenta) {
		return estadoCuentaRepository.save(estadocuenta);
	}

}
