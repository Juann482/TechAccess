package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Excusas;

@Repository
public interface ExcusasRepository extends JpaRepository<Excusas, Long> {

}
