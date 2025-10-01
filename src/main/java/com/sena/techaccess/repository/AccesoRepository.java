package com.sena.techaccess.repository;

import com.sena.techaccess.model.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer> {
}
