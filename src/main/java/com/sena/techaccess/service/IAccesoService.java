package com.sena.techaccess.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sena.techaccess.model.Acceso;

public interface IAccesoService {

    // CRUD Básico
    Optional<Acceso> findById(Integer id);
    Acceso save(Acceso acceso);
    void delete(Integer id);
    List<Acceso> findAll();
    
    // Búsquedas específicas
    Acceso findByHoraIngreso(LocalDateTime horaIngreso);
    List<Acceso> findByHoraEgreso(LocalDateTime horaEgreso);
    Acceso findUltimoAcceso(Integer usuarioId);
    Acceso findUltimoAccesoActivo(Integer usuarioId);
    
    // Para vistas
    Map<Integer, Acceso> findLatestAccessForAllUsers();
    
    // Registro automático
    Acceso registrarAcceso(Integer usuarioId);
    
    // Filtrado para vista Ingresos
    Page<Acceso> filtrarUsuariosIngresos(Integer usuarioId, String nombre, String documento, 
            String rol, String ficha, LocalDate fechaIngreso, 
            LocalDate fechaEgreso, Pageable pageable);
    
    // Nuevo método para obtener accesos con usuario cargado
    List<Acceso> findAllWithUsuario();

}