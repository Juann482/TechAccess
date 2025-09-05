package com.sena.techaccess.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.EstadoPermanencia;

@Service	
public class AccesoServiceImplement implements IAccesoService{

	
	@Override 
	public EstadoPermanencia save(Acceso acceso) {
    // TODO: Implementa la lógica real
    throw new UnsupportedOperationException("Método no implementado todavía");
	}


	@Override
	public List<Acceso> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EstadoPermanencia save(EstadoPermanencia estadoPermanencia) {
		// TODO Auto-generated method stub
		return null;
	}
}