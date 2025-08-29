package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Vigilancia;

@Repository
public interface VigilanciaRepository extends JpaRepository<Vigilancia, Integer>{

}