package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.EstadoCuenta;

@Repository
public interface EstadoCuentaRepository extends JpaRepository<EstadoCuenta, Integer> {

}