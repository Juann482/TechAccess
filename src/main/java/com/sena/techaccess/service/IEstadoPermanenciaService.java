package com.sena.techaccess.service;

import com.sena.techaccess.model.EstadoPermanencia;

public interface IEstadoPermanenciaService {
	
	void ingresoPermanencias();
	EstadoPermanencia save(EstadoPermanencia estadoPermanencia);

}
