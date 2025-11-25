
package com.sena.techaccess.service;

import com.sena.techaccess.model.Bibliotecario;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface IBibliotecarioService {

	List<Bibliotecario> listarTodos();

	Page<Bibliotecario> listarPaginado(int pagina, int tamano, String busqueda, String estado);

	Optional<Bibliotecario> obtenerPorId(Integer id);

	Bibliotecario guardar(Bibliotecario libro);

	void eliminar(Integer id);

	void cambiarEstado(Integer id);

	Bibliotecario findById(Integer id);
}