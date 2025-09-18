package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import com.sena.techaccess.model.EstadoCuenta;

public interface IEstadoCuentaService {

	Optional<EstadoCuenta> findById(Integer idEstado);

	List<EstadoCuenta> findAll();
}
