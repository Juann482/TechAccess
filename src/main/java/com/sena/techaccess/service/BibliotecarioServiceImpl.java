package com.sena.techaccess.service;

import com.sena.techaccess.model.Bibliotecario;
import com.sena.techaccess.repository.BibliotecarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioServiceImpl implements IBibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Override
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

    @Override
    public Page<Bibliotecario> listarPaginado(Pageable pageable, String busqueda, String estado) {
        if ((busqueda == null || busqueda.isEmpty()) && (estado == null || estado.isEmpty())) {
            return bibliotecarioRepository.findAll(pageable);
        }
        Bibliotecario.Estado estadoEnum = null;
        if (estado != null && !estado.isEmpty()) {
            try {
                estadoEnum = Bibliotecario.Estado.valueOf(estado.toUpperCase());
            } catch (IllegalArgumentException e) {
            }
        }
        return bibliotecarioRepository.buscarConFiltros(busqueda, estado, pageable);
    }

    @Override
    public Optional<Bibliotecario> obtenerPorId(Integer id) {
        return bibliotecarioRepository.findById(id);
    }

    @Override
    public Bibliotecario findById(Integer id) {
        return obtenerPorId(id).orElse(null);
    }

    @Override
    public Bibliotecario guardar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    @Override
    public void eliminar(Integer id) {
        bibliotecarioRepository.deleteById(id);
    }

	@Override
	@Transactional
	public void cambiarEstado(Integer id) {
		bibliotecarioRepository.cambiarEstado(id);
	}
}