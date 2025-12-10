package com.sena.techaccess.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Usuario;

public interface IUsuarioService {

	Optional<Usuario> get(Integer id);

	Usuario save(Usuario usuario);

	List<Usuario> findByEstadoCuenta(String estadoCuenta);

	void delete(Integer id);

	void update(Usuario usuario);

	Usuario findByNombre(String nombre);

	Optional<Usuario> findByEmail(String email);

	List<Usuario> findByRol(String rol);

	Usuario findByDocumento(String documento);
	
	List<Usuario> findByRolAndEstadoCuenta(String rol, String estadoCuenta);

	Page<Usuario> filtrarUsuarios(String nombre, String documento, String rol, String estado, Pageable pageable);

	List<Usuario> findAll();
	
	List<Usuario> findByFichaId(Integer id);
	
	Page<Usuario> findByFichaId(Integer fichaId, Pageable pageable);
	
	Page<Usuario> filtrarUsuariosEnFicha(Integer fichaId, String nombre, String documento, String rol, String estado, Pageable pageable);

	Optional<Usuario> findById(Integer id);
	
	void saveAll(List<Usuario> usuarios);
	
	Map<String, Long> obtenerUsuariosActivosPorRol();

	// >>>>>>>DASHBOARD<<<<<<<<

	int Inactivos();
	int Activos();

	int Aprendiz();
	int AprendizAct();
	int AprendizIN();
	
	int Instructor();
	int InstructorAc();
	int InstructorIN();

	int Visitante();
	int VisitantesAc();
	int VisitantesIN();

	int totalUsuarios();

	void actualizarEstado(Integer id, String estado);
	
	//>>>>>> VIGILANCIA <<<<<<<<<
	

}