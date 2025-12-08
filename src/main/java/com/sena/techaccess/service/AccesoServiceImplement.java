package com.sena.techaccess.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.repository.AccesoRepository;

@Service
public class AccesoServiceImplement implements IAccesoService {

	@Autowired
	private AccesoRepository accesoRepository;

	@Override
	public Optional<Acceso> get(Integer id) {
		return accesoRepository.findById(id);
	}

	@Override
	public Acceso findbyHoraIngreso(LocalDateTime horaIngreso) {
		return accesoRepository.findByHoraIngreso(horaIngreso);
	}

	@Override
	public Acceso findByHoraEgreso(LocalDateTime horaEgreso) {
		return accesoRepository.findByHoraEgreso(horaEgreso);
	}

	@Override
	public Acceso save(Acceso acceso) {
		return accesoRepository.save(acceso);
	}

	@Override
	public void update(Acceso acceso) {
		accesoRepository.save(acceso);
	}

	@Override
	public void delete(Integer id) {
		accesoRepository.deleteById(id);
	}

	@Override
	public List<Acceso> findAll() {
		return accesoRepository.findAll();
	}

	// 🔍 ÚLTIMO ACCESO SIN SALIDA (para registrar egreso)
	@Override
	public Acceso findUltimoAcceso(Integer idUsuario) {
		return accesoRepository.findTopByUsuario_IdAndHoraEgresoIsNullOrderByHoraIngresoDesc(idUsuario);
	}

	// 🔍 ÚLTIMO ACCESO de todos los usuarios (para paneles)
	@Override
	public Map<Integer, Acceso> findLatestAccessForAllUsers() {
		List<Object[]> results = accesoRepository.findLatestAccessForAllUsers();
		Map<Integer, Acceso> latestAccessMap = new HashMap<>();

		if (results != null) {
			for (Object[] result : results) {
				if (result != null && result.length >= 2) {
					Integer userId = (Integer) result[0];
					Acceso acceso = (Acceso) result[1];
					if (userId != null && acceso != null) {
						latestAccessMap.put(userId, acceso);
					}
				}
			}
		}
		return latestAccessMap;
	}

	// Alias si alguna parte del código llama a este nombre
	@Override
	public Map<Integer, Acceso> findUltimoAcceso() {
		return findLatestAccessForAllUsers();
	}

	// Métodos sobrantes: devuelve null solo si NO se usan en ningún sitio.
	@Override
	public Acceso findByHoraIngreso(Integer id) {
		return null;
	}

}
