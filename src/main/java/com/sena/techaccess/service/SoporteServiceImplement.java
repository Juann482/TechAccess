package com.sena.techaccess.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.model.Usuario;

@Service
public class SoporteServiceImplement implements ISoporteService {

	@Override
	public Optional<Soporte> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Soporte save(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario findByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
