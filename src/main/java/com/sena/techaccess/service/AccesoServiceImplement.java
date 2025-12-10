package com.sena.techaccess.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.AccesoRepository;

@Service
@Transactional
public class AccesoServiceImplement implements IAccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Acceso> findById(Integer id) {
        return accesoRepository.findById(id);
    }

    @Override
    @Transactional
    public Acceso save(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        accesoRepository.deleteById(id);
    }

    @Override
    public List<Acceso> findByUsuarioIdOrderByHoraIngresoDesc(Long usuarioId) {
        return accesoRepository.findByUsuarioIdOrderByHoraIngresoDesc(usuarioId);
    }

    
    @Override
    public List<Acceso> findByUsuarioAndMes(Long userId, int mes, int anio) {
        return accesoRepository.findByUsuarioAndMes(userId, mes, anio);
    }

    @Override
    public List<Acceso> findByUsuarioAndRango(Long userId, LocalDateTime inicio, LocalDateTime fin) {
        return accesoRepository.findByUsuarioAndRango(userId, inicio, fin);
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<Acceso> findAll() {
        return accesoRepository.findAllWithUsuario(); // Usa la versión con JOIN FETCH
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Acceso> findAllWithUsuario() {
        return accesoRepository.findAllWithUsuario();
    }

    @Override
    @Transactional(readOnly = true)
    public Acceso findByHoraIngreso(LocalDateTime horaIngreso) {
        return accesoRepository.findByHoraIngreso(horaIngreso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Acceso> findByHoraEgreso(LocalDateTime horaEgreso) {
        // Este método estaba mal en el repositorio, lo corregimos
        return List.of(accesoRepository.findByHoraEgreso(horaEgreso));
    }

    @Override
    @Transactional(readOnly = true)
    public Acceso findUltimoAcceso(Integer usuarioId) {
        return accesoRepository.findTopByUsuarioIdOrderByHoraIngresoDesc(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Acceso> findLatestAccessForAllUsers() {
        Map<Integer, Acceso> mapa = new HashMap<>();
        List<Acceso> lista = accesoRepository.findLatestAccessForAllUsers();
        
        for (Acceso a : lista) {
            if (a.getUsuario() != null) {
                mapa.put(a.getUsuario().getId(), a);
            }
        }
        return mapa;
    }

    @Override
    @Transactional(readOnly = true)
    public Acceso findUltimoAccesoActivo(Integer usuarioId) {
        return accesoRepository.findTopByUsuarioIdAndHoraEgresoIsNullOrderByHoraIngresoDesc(usuarioId);
    }

    @Override
    @Transactional
    public Acceso registrarAcceso(Integer usuarioId) {
        Acceso ultimo = findUltimoAccesoActivo(usuarioId);

        // Si hay ingreso sin salida → registrar salida
        if (ultimo != null && ultimo.getHoraEgreso() == null) {
            ultimo.setHoraEgreso(LocalDateTime.now());
            ultimo.setActividad("Egresado");
            return accesoRepository.save(ultimo);
        }

        // Si no hay acceso activo → ingresar
        Acceso nuevo = new Acceso();
        Usuario u = new Usuario();
        u.setId(usuarioId);
        nuevo.setUsuario(u);
        nuevo.setHoraIngreso(LocalDateTime.now());
        nuevo.setActividad("Ingresado");

        return accesoRepository.save(nuevo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Acceso> filtrarUsuariosIngresos(Integer usuarioId, String nombre, String documento, 
                                                String rol, String ficha, LocalDate fechaIngreso, 
                                                LocalDate fechaEgreso, Pageable pageable) {
        return accesoRepository.filtrarUsuariosIngresos(usuarioId, nombre, documento, rol, 
                                                       ficha, fechaIngreso, fechaEgreso, pageable);
    }
    
    // Métodos obsoletos que eliminamos (mantén compatibilidad si es necesario)
    @Deprecated
    public void update(Acceso acceso) {
        save(acceso);
    }
    
    @Deprecated
    public Optional<Acceso> get(Integer id) {
        return findById(id);
    }
    
    @Deprecated
    public Acceso findByHoraIngreso(Integer id) {
        return findById(id).orElse(null);
    }
    
    @Deprecated
    public Map<Integer, Acceso> findUltimoAcceso() {
        return findLatestAccessForAllUsers();
    }
    
    @Deprecated
    public List<Acceso> findbyHoraIngreso(Integer usuarioId) {
        return accesoRepository.findByUsuarioIdOrderByHoraIngresoDesc(usuarioId);
    }
    
    @Deprecated
    public List<Acceso> findbyHoraIAcceso(Integer usuarioId) {
        return accesoRepository.findByUsuarioIdOrderByHoraIngresoDesc(usuarioId);
    }
    
    @Deprecated
    public Acceso buscarUltimoAccesoPorUsuario(Integer usuarioId) {
        return findUltimoAcceso(usuarioId);
    }

	@Override
	public List<Acceso> findByUsuarioIdOrderByHoraIngresoDesc(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acceso> findByUsuarioAndMes(Integer id, int mes, int anio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findByDocumento(String correo) {
		// TODO Auto-generated method stub
		return null;
	}
}

