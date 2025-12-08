package com.sena.techaccess.repository;

import com.sena.techaccess.model.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Integer> {

	// Último acceso sin salida de un usuario (lo que usa findUltimoAcceso)
	Acceso findTopByUsuario_IdAndHoraEgresoIsNullOrderByHoraIngresoDesc(Integer usuarioId);

	// (opcional) último acceso de un usuario (con o sin salida)
	Acceso findTopByUsuarioIdOrderByHoraIngresoDesc(Integer usuarioId);

	@Query("SELECT a.usuario.id as userId, a FROM Acceso a "
			+ "INNER JOIN (SELECT acceso.usuario.id as uid, MAX(acceso.horaIngreso) as maxHora "
			+ "FROM Acceso acceso GROUP BY acceso.usuario.id) latest "
			+ "ON a.usuario.id = latest.uid AND a.horaIngreso = latest.maxHora")
	List<Object[]> findLatestAccessForAllUsers();

	Acceso findByHoraIngreso(LocalDateTime horaIngreso);

	Acceso findByHoraEgreso(LocalDateTime horaEgreso);
}