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

	@Override
	public void delete(Integer idFicha) { // Elimina una ficha por su ID
		fichaRepository.deleteById(idFicha);
	}

	public Optional<Ficha> findById(Integer idFicha) { // Busca una ficha su ID
		return fichaRepository.findById(idFicha);
	}

	public List<Ficha> findAll() { // Obtiene todas las fichas registradas
		return fichaRepository.findAll();
	}

	public Ficha findByNumFicha(Integer numFicha) { // Busca ficha por número de ficha
		return fichaRepository.findByNumFicha(numFicha);
	}

	public List<Usuario> findUsuariosByIdFicha(Integer idFicha) { // Obtiene usuarios asociados a una ficha
		return fichaRepository.findUsuariosByIdFicha(idFicha);
	}

	public void update(Ficha ficha) {
		fichaRepository.save(ficha);		
	}

	@Override
	public Optional<Ficha> get(Integer idFicha) {
		// TODO Auto-generated method stub
		return fichaRepository.findById(idFicha);
	}

	@Override
	public Ficha findByNombrePrograma(String nombrePrograma) {
		// TODO Auto-generated method stub
		return fichaRepository.findByNombrePrograma(nombrePrograma);
	}
	
}
