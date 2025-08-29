package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.EstadoPermanencia;

@Repository
public interface EstadoPermanenciaRepository extends JpaRepository<EstadoPermanencia, Integer>{

}