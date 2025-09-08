package com.sena.techaccess.service;

import com.sena.techaccess.model.EstadoCuenta;

public interface IEstadoCuentaService {
	
	void inicializarEstados();	
	EstadoCuenta save(EstadoCuenta estadocuenta);

}
