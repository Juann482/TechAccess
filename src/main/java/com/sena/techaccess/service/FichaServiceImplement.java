package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.repository.UsuarioRepository;

@Service
public class FichaServiceImplement implements IFichaService {

	@Autowired
	private FichaRepository fichaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Ficha save(Ficha ficha) { // Guarda o actualiza una ficha
		return fichaRepository.save(ficha);
	}

	@Override
	public void delete(Integer id) { // Elimina una ficha por su ID
		fichaRepository.deleteById(id);
	}

	public Optional<Ficha> findById(Integer id) { // Busca una ficha su ID
		return fichaRepository.findById(id);
	}

	public List<Ficha> findAll() { // Obtiene todas las fichas registradas
		return fichaRepository.findAll();
	}

	/*public Ficha findByNumFicha(String numFicha) { // Busca ficha por número de ficha
		return fichaRepository.findByNumFicha(numFicha);
	}*/

	public void update(Ficha ficha) {
		fichaRepository.save(ficha);		
	}

	@Override
	public Ficha findByNombrePrograma(String nombrePrograma) {
		return fichaRepository.findByNombrePrograma(nombrePrograma);
	}

	@Override
	public Optional<Ficha> get(Integer id) {
		return fichaRepository.findById(id);
	}

	@Override
	public List<Ficha> findByEstado(String estado) {
		return fichaRepository.findByEstado(estado);
	}

	@Override
	public Ficha findByJornada(String jornada) {
		return fichaRepository.findByJornada(jornada);
	}
	
	public List<Usuario> listarUsuariosPorFicha(Integer fichaId) {
	    return usuarioRepository.findByFichaId(fichaId);
	}

	@Override
	public Page<Ficha> filtrarFicha(String nombrePrograma, String jornada, String estado, Integer numFicha,
			Pageable pageable) {
		return fichaRepository.filtrarficha(nombrePrograma, jornada, estado, numFicha, pageable);
	}

}
