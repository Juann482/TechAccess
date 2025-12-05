
package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Usuario;

public interface IExcusasService {
	
	public Excusas save(Excusas excusas);

	public void delete(Integer id);

	public Optional<Excusas> findById(Integer id);

	List<Excusas> findAll();

	public List<Excusas> findByUsuario(Usuario user);

	
}
