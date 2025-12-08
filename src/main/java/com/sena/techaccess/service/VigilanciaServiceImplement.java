package com.sena.techaccess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.Vigilancia;
import com.sena.techaccess.repository.VigilanciaRepository;

@Service
public class VigilanciaServiceImplement implements IVigilanciaService {

	@Autowired
	private VigilanciaRepository vigilanciaRepository;

	// =======================================================================================

	@Override
	public void registrarVigilancia(Vigilancia v) {
		// TODO Auto-generated method stub
		
	} 
	
	// =========================================================================================
		
}
