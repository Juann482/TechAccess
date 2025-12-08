package com.sena.techaccess.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sena.techaccess.model.Acceso;

public interface IAccesoService {

	Optional<Acceso> get(Integer id);

	Acceso findbyHoraIngreso(LocalDateTime horaIngreso);

	Acceso findByHoraEgreso(LocalDateTime horaEgreso);

	Acceso save(Acceso acceso);

	void update(Acceso acceso);

	void delete(Integer id);

	public List<Acceso> findAll();

	Acceso findByHoraIngreso(Integer id);

	// Último acceso SIN salida de un usuario (para egreso)
	Acceso findUltimoAcceso(Integer idUsuario);

	Map<Integer, Acceso> findLatestAccessForAllUsers();

	Map<Integer, Acceso> findUltimoAcceso();

}