package com.sena.techaccess.service;

import com.sena.techaccess.model.Bibliotecario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBibliotecarioService {
    List<Bibliotecario> listarTodos();
    Page<Bibliotecario> listarPaginado(Pageable pageable, String busqueda, String estado);
    Optional<Bibliotecario> obtenerPorId(Integer id);
    Bibliotecario findById(Integer id);
    Bibliotecario guardar(Bibliotecario bibliotecario);
    void eliminar(Integer id);
    void cambiarEstado(Integer id);
}