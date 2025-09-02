package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.FichaRepository;

@Service
public class FichaServiceImplement implements IFichaService {

	@Autowired
	private FichaRepository fichaRepository;

	public Ficha save(Ficha ficha) { // Guarda o actualiza una ficha
		return fichaRepository.save(ficha);
	}

	public void delete(Integer idFicha) { // Elimina una ficha por su ID
		fichaRepository.deleteById(idFicha);
	}

	public Optional<Ficha> findById(Integer idFicha) { // Busca una ficha su ID
		return fichaRepository.findById(idFicha);
	}

	public List<Ficha> findAll() { // Obtiene todas las fichas registradas
		return fichaRepository.findAll();
	}

	public List<Ficha> findByNombrePrograma(String nombrePrograma) { // Busca fichas por nombre del programa
		return fichaRepository.findByNombrePrograma(nombrePrograma);
	}

	public Ficha findByNuumFicha(String nuumFicha) { // Busca ficha por número de ficha
		return fichaRepository.findByNuumFicha(nuumFicha);
	}

	public List<Usuario> findUsuariosByIdFicha(Integer idFicha) { // Obtiene usuarios asociados a una ficha
		return fichaRepository.findUsuariosByIdFicha(idFicha);
	}
}
