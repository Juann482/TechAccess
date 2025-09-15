package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.EstadoCuenta;

public interface IEstadoCuentaService {
	
	List<EstadoCuenta>finbyId(EstadoCuenta tipoEstado);
	

	Optional<EstadoCuenta> findBy(Integer idEstado);

}
