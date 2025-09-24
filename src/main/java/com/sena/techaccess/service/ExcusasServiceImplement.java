package com.sena.techaccess.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.repository.ExcusasRepository;

@Service
public class ExcusasServiceImplement implements IExcusasService {

	@Autowired
	private ExcusasRepository excusasRepository;

	@Override
	public Excusas save(Excusas excusa) {
		return excusasRepository.save(excusa);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		return excusasRepository.deleteById(id);

	}

	@Override
	public Optional<Excusas> findById(Integer id) {
		// TODO Auto-generated method stub
		return excusasRepository.findById(id);
	}

	@Override
	public List<Excusas> findByIdExcusas(Integer idExcusas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Excusas> findAll() {
		// TODO Auto-generated method stub
		return excusasRepository.findAll();
	}

}
