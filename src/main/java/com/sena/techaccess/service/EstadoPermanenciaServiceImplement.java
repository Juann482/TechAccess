package com.sena.techaccess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.EstadoPermanencia;
import com.sena.techaccess.repository.EstadoPermanenciaRepository;

@Service
public class EstadoPermanenciaServiceImplement implements IEstadoPermanenciaService{
	
	@Autowired
	private EstadoPermanenciaRepository estadoPermanenciaRepository;
	
	@Override
	public void ingresoPermanencias() {
		
		if (!estadoPermanenciaRepository.existsById(EstadoPermanencia.P_ID_PRESENTE)) {
			EstadoPermanencia presente = new EstadoPermanencia();
			presente.setIdestadoPermanencia(EstadoPermanencia.P_ID_PRESENTE);
			presente.setTipoPermanencia(EstadoPermanencia.P_PRESENTE);
			estadoPermanenciaRepository.save(presente);
		}
		
		if (!estadoPermanenciaRepository.existsById(EstadoPermanencia.P_ID_AUSENTE)) {
			EstadoPermanencia ausente = new EstadoPermanencia();
			ausente.setIdestadoPermanencia(EstadoPermanencia.P_ID_AUSENTE);
			ausente.setTipoPermanencia(EstadoPermanencia.P_AUSENTE);
			estadoPermanenciaRepository.save(ausente);			
		}
		
		if (!estadoPermanenciaRepository.existsById(EstadoPermanencia.P_ID_POR_DEFINIR)) {
			EstadoPermanencia por_definir = new EstadoPermanencia();
			por_definir.setIdestadoPermanencia(EstadoPermanencia.P_ID_POR_DEFINIR);
			por_definir.setTipoPermanencia(EstadoPermanencia.P_POR_DEFINIR);
			estadoPermanenciaRepository.save(por_definir);						
		}
		
	}
	
	@Override
	public EstadoPermanencia save(EstadoPermanencia estadoPermanencia) {
		return estadoPermanenciaRepository.save(estadoPermanencia);
	}

}
