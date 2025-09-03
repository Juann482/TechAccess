package com.sena.techaccess.service;

import java.util.List;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.EstadoPermanencia;

public interface IAccesoService {

	public List<Acceso> findAll();

	EstadoPermanencia save(Acceso acceso);

	EstadoPermanencia save(EstadoPermanencia estadoPermanencia);

}