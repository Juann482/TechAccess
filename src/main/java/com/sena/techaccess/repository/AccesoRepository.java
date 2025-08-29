package com.sena.techaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.model.Acceso;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer>{

	List<Acceso> findByUsuario(Usuario usuario);
}