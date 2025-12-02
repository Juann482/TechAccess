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

	@Query("SELECT a FROM Acceso a WHERE a.usuario.id = :usuarioId ORDER BY a.id DESC LIMIT 1")
	Acceso findTopByUsuarioIdOrderByIdDesc(@Param("usuarioId") Integer usuarioId);

	// Encuentra el último acceso sin salida de un usuario
	Acceso findTopByUsuarioIdAndHoraEgresoIsNullOrderByHoraIngresoDesc(Integer usuarioId);

	// Encuentra el último acceso de un usuario (con o sin salida)
	Acceso findTopByUsuarioIdOrderByHoraIngresoDesc(Integer usuarioId);

	// Encuentra el último acceso para todos los usuarios
	@Query("SELECT a.usuario.id as userId, a FROM Acceso a " +
			"INNER JOIN (SELECT usuario.id as uid, MAX(acceso.horaIngreso) as maxHora " +
			"FROM Acceso acceso GROUP BY acceso.usuario.id) latest " +
			"ON a.usuario.id = latest.uid AND a.horaIngreso = latest.maxHora")
	List<Object[]> findLatestAccessForAllUsers();

	// Métodos existentes...
	Acceso findByHoraIngreso(LocalDateTime horaIngreso);

	Acceso findByHoraEgreso(LocalDateTime horaEgreso);

	// Eliminar o corregir este método si no es necesario
	// Acceso findTopByUsuarioIdOrderByIdDesc(Integer usuarioId);
}