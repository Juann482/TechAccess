
package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import com.sena.techaccess.model.Excusas;

public interface IExcusasService {
	
	public Excusas save(Excusas excusas);

	public void delete(Integer id);

	public Optional<Excusas> findById(Integer id);

	List<Excusas> findAll();

}
