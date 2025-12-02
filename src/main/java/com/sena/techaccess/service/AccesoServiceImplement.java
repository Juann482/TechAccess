package com.sena.techaccess.service;

import java.time.LocalDateTime;
import java.util.List;
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

    // ============================= NUEVO ===============================
    // 🔍 Obtener el último registro de acceso de un usuario
    @Override
    public Acceso findUltimoAcceso(Integer idUsuario) {
        return accesoRepository.findTopByUsuarioIdOrderByHoraIngresoDesc(idUsuario);
    }

	@Override
	public Acceso findbyHoraIngreso(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
