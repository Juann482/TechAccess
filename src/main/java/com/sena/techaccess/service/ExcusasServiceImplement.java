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
	    public Excusas save(Excusas excusas) {
	        return excusasRepository.save(excusas);
	    }

	    @Override
	    public void delete(Integer id) {
	        excusasRepository.deleteById(id);
	    }

	    @Override
	    public Optional<Excusas> findById(Integer id) {
	        return excusasRepository.findById(id);
	    }

	    @Override
	    public List<Excusas> findAll() {
	        return excusasRepository.findAll();
	    }	

}
