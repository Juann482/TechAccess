package com.sena.techaccess.service;

import com.sena.techaccess.model.Bibliotecario;
import com.sena.techaccess.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioServiceImpl implements IBibliotecarioService {

	@Autowired
	private BibliotecarioRepository libroRepository;

	@Override
	public List<Bibliotecario> listarTodos() {
		return libroRepository.findAll();
	}

	// Método optimizado para manejar paginación y filtros con una sola consulta
	// @Query
	@Override
	public Page<Bibliotecario> listarPaginado(int pagina, int tamano, String busqueda, String estado) {

		Pageable pageable = PageRequest.of(pagina, tamano);

		// 1. Preparar el Enum de Estado (será null si no se envió o es inválido)
		Bibliotecario.EstadoLibro estadoEnum = null;
		if (estado != null && !estado.isEmpty()) {
			try {
				estadoEnum = Bibliotecario.EstadoLibro.valueOf(estado.toUpperCase());
			} catch (IllegalArgumentException ignored) {
				// Si el valor de estado no coincide con el Enum, se ignora el filtro.
			}
		}

		// 2. Preparar el término de búsqueda (será null si está vacío)
		String terminoBusqueda = (busqueda != null && !busqueda.isEmpty()) ? busqueda : null;

		// 3. Llamar a la consulta JPQL única.
		// Esta consulta maneja internamente la lógica de si 'terminoBusqueda' o
		// 'estadoEnum' son nulos.
		return libroRepository.buscarLibrosConFiltros(terminoBusqueda, estadoEnum, pageable);
	}

	@Override
	public Optional<Bibliotecario> obtenerPorId(Integer id) {
		return libroRepository.findById(id);
	}

	@Override
	public Bibliotecario guardar(Bibliotecario libro) {
		return libroRepository.save(libro);
	}

	@Override
	public void eliminar(Integer id) {
		libroRepository.deleteById(id);
	}

	@Override
	public void cambiarEstado(Integer id) {
		libroRepository.cambiarEstado(id);
	}

	// Método corregido para cumplir con la interfaz ILibroService
	@Override
	public Bibliotecario findById(Integer id) {
		return libroRepository.findById(id).orElse(null);
	}
}