package com.sena.techaccess.repository;

import com.sena.techaccess.model.Acceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer> {

	Acceso findByHoraIngreso(java.time.LocalDateTime horaIngreso);

	Acceso findByHoraEgreso(java.time.LocalDateTime horaEgreso);

	// obtener el último acceso del usuario

	Acceso findTopByUsuarioIdOrderByHoraIngresoDesc(Integer usuarioId);
}
