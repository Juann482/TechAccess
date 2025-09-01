package com.sena.techaccess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.EstadoPermanencia;
import com.sena.techaccess.model.Vigilancia;
import com.sena.techaccess.repository.EstadoPermanenciaRepository;
import com.sena.techaccess.repository.VigilanciaRepository;

@Service
public class VigilanciaServiceImplement implements IVigilanciaService {

	@Autowired
	private EstadoPermanenciaRepository estadoPermanenciaRepository;
	@Autowired
	private VigilanciaRepository vigilanciaRepository;

	// ========================================================================================

	@Override
	public void registrarVigilancia(Vigilancia v) {
		registrarVigilanciaConEstado(v, EstadoPermanencia.P_ID_PRESENTE);
	}

	public void registrarVigilanciaConEstado(Vigilancia v, Integer idEstado) {
		EstadoPermanencia ep = estadoPermanenciaRepository.findById(idEstado)
				.orElseThrow(() -> new RuntimeException("Estado no encontrado"));
		
		v.setEstadoPermanencia(ep);
		
		vigilanciaRepository.save(v);
	} 
	
	// =========================================================================================
		
}
