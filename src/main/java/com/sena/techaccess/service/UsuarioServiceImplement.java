package com.sena.techaccess.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*import java.util.stream.Collectors;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.PermisosRepository;
import com.sena.techaccess.repository.UsuarioRepository;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PermisosRepository permisoRepository;

	// >>>>>>>>>>>>>>>> DASHBOARD <<<<<<<<<<<<<<<<<

	@Override
	public int totalUsuarios() {
		return (int) usuarioRepository.count();
	}

	@Override
	public int Inactivos() {
		return (int) usuarioRepository.countByEstadoCuenta("Inactivo");
	}

	@Override
	public int Activos() {
		return (int) usuarioRepository.countByEstadoCuenta("Activo");
	}

	@Override
	public int Aprendiz() {
		return (int) usuarioRepository.countByRol("Aprendiz");
	}
	
	@Override
	public int AprendizAct() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Aprendiz", "Activo");
	}

	@Override
	public int AprendizIN() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Aprendiz", "Inactivo");
	}
	@Override
	public int Instructor() {
		return (int) usuarioRepository.countByRol("Instructor");
	}
	
	@Override
	public int InstructorAc() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Instructor", "Activo");
	}
	
	@Override
	public int InstructorIN() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Instructor", "Inactivo");
	}

	@Override
	public int Visitante() {
		return (int) usuarioRepository.countByRol("Visitantes");
	}
	
	@Override
	public int VisitantesAc() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Visitantes", "Activo");
	}
	
	@Override
	public int VisitantesIN() {
		return (int) usuarioRepository.countByRolAndEstadoCuenta("Visitantes", "Inactivo");
	}

//>>>>>>>>>>>>>>>>>>>>>> FIN DASHBOARD <<<<<<<<<<<<<<<<<<<<<

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> get(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public void update(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	// @Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Integer id) {
		permisoRepository.deleteByUsuarioId(id);
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario findByNombre(String nombre) {
		return usuarioRepository.findByNombre(nombre);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findByEstadoCuenta(String estadoCuenta) {
		return usuarioRepository.findByEstadoCuenta(estadoCuenta);
	}

	@Override
	public List<Usuario> findByRol(String rol) {
		return usuarioRepository.findByRol(rol);
	}

	@Override
	public Usuario findByDocumento(String documento) {
		return usuarioRepository.findByDocumento(documento);
	}

	@Override
	public Page<Usuario> filtrarUsuarios(String nombre, String documento, String rol, String estado,
			Pageable pageable) {
		return usuarioRepository.filtrarUsuarios(nombre, documento, rol, estado, pageable);
	}

	@Override
	public Page<Usuario> findByFichaId(Integer fichaId, Pageable pageable) {
		return usuarioRepository.findByFichaId(fichaId, pageable);
	}

	@Override
	public void saveAll(List<Usuario> usuarios) {
		usuarioRepository.saveAll(usuarios);
	}

	@Override
	public Page<Usuario> filtrarUsuariosEnFicha(Integer fichaId, String nombre, String documento, String rol,
			String estado, Pageable pageable) {
		return usuarioRepository.filtrarUsuariosEnFicha(fichaId, nombre, documento, rol, estado, pageable);
	}

	@Override
	public List<Usuario> findByFichaId(Integer id) {
		return usuarioRepository.findByFichaId(id);
	}

	@Override
	public Map<String, Long> obtenerUsuariosActivosPorRol() {

		List<Object[]> resultados = usuarioRepository.contarUsuariosActivosPorRol();
		Map<String, Long> mapa = new HashMap<>();
		for (Object[] fila : resultados) {
			String rol = (String) fila[0];
			Long cantidad = (Long) fila[1];
			mapa.put(rol, cantidad);
		}

		return mapa;
	}

	@Override
	public List<Usuario> findByRolAndEstadoCuenta(String rol, String estadoCuenta) {
		return usuarioRepository.findByRolAndEstadoCuenta(rol, estadoCuenta);
	}

	@Override
	public int AprendizAct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int AprendizIN() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int InstructorAc() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int InstructorIN() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int VisitantesAc() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int VisitantesIN() {
		// TODO Auto-generated method stub
		return 0;
	}

}
