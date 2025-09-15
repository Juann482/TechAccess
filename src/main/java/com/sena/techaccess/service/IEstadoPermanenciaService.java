package com.sena.techaccess.service;

import java.util.List;

import com.sena.techaccess.model.EstadoPermanencia;

public interface IEstadoPermanenciaService {

	EstadoPermanencia save(EstadoPermanencia estadoPermanencia);
	
	List<EstadoPermanencia>findByTipoPermanencia(EstadoPermanencia tipoPermanencia);
	
	

}
