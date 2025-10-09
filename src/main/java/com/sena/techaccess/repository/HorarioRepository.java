package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
}
