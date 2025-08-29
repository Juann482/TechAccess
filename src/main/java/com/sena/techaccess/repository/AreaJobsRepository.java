package com.sena.techaccess.repository;

import com.sena.techaccess.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.AreaJobs;

@Repository
public interface AreaJobsRepository extends JpaRepository<AreaJobs, Integer>{

	List<AreaJobs> findByUsuario(Usuario usuario);
	
}