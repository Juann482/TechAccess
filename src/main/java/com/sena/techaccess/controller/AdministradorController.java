package com.sena.techaccess.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.UploadFileService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Administrador")
public class AdministradorController {

	private final PasswordEncoder passwordEncoder;

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;

	@Autowired
	private HttpSession session;

	@Autowired
	private UploadFileService upload;

	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

	AdministradorController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	// ========================== DASHBOARD ========================

	@GetMapping("/dashboard")
	public String Dashboard(Model model) {

		int TotalUSER = usuarioService.totalUsuarios();
		int Inactivos = usuarioService.Inactivos();
		int Activos = usuarioService.Activos();

		int Aprendiz = usuarioService.Aprendiz();
		int AprendizAc = usuarioService.AprendizAct();
		int AprendizIn = usuarioService.AprendizIN();

		int Instructor = usuarioService.Instructor();
		int InstructorAc = usuarioService.InstructorAc();
		int InstructorIn = usuarioService.InstructorIN();

		int Visitante = usuarioService.Visitante();
		int VisitanteAc = usuarioService.VisitantesAc();
		int VisitanteIn = usuarioService.VisitantesIN();

		model.addAttribute("UsuariosA", TotalUSER);
		model.addAttribute("Inactivos", Inactivos);
		model.addAttribute("Activos", Activos);

		model.addAttribute("Aprendiz", Aprendiz);
		model.addAttribute("AprendizAc", AprendizAc);
		model.addAttribute("ApInactivo", AprendizIn);

		model.addAttribute("Instructor", Instructor);
		model.addAttribute("AcInstructor", InstructorAc);
		model.addAttribute("InInactivo", InstructorIn);

		model.addAttribute("Visitante", Visitante);
		model.addAttribute("VisitAc", VisitanteAc);
		model.addAttribute("VisitEg", VisitanteIn);

		Map<String, Long> datos = usuarioService.obtenerUsuariosActivosPorRol();

		model.addAttribute("labels", datos.keySet());
		model.addAttribute("data", datos.values());

		List<Usuario> vigi = usuarioService.findByRolAndEstadoCuenta("Vigilancia", "Activo");
		model.addAttribute("vigilancia", vigi);

		return "Administrador/Dashboard";
	}

	// ========================== USUARIO =========================

	@GetMapping("/form")
	public String mostrarFormulario(Usuario usuario, Model model) {

		model.addAttribute("usuarios", usuarioService.findAll());
		model.addAttribute("usuario", new Usuario()); // objeto vacio
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas

		return "Administrador/RegistroUSER";
	}

	@PostMapping("/form")
	public String guardarRegistro(Usuario usuario, RedirectAttributes redirect) {

		usuario.setPassword(pe.encode(usuario.getPassword()));
		usuario.setEstadoCuenta("Activo");
		if (usuario.getFicha() != null && usuario.getFicha().getId() == null) {
			usuario.setFicha(null);
		}
		usuarioService.save(usuario);
		LOGGER.warn("Usuario guardado: {}", usuario.getNombre());

		redirect.addFlashAttribute("mensaje", "Usuario registrado correctamente");
		redirect.addFlashAttribute("tipo", "success");

		return "redirect:/Administrador/usuarios";
	}

	@GetMapping("/cambiarEstado/{id}")
	public String cambiarEstado(@PathVariable Integer id) {

		Usuario u = usuarioService.get(id).orElse(null);
		if (u != null) {
			if ("Activo".equalsIgnoreCase(u.getEstadoCuenta())) {
				u.setEstadoCuenta("Inactivo");
			} else {
				u.setEstadoCuenta("Activo");
			}
		}

		usuarioService.save(u);

		return "redirect:/Administrador/usuarios"; // Cambia esto a tu ruta real
	}

	// Enlistado de usuarios
	@GetMapping("/usuarios")
	public String enlistarUsuarios(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String documento, @RequestParam(required = false) String rol,
			@RequestParam(required = false) String estado, @RequestParam(defaultValue = "0") int page, // esto me
																										// ayudara con
																										// la pagina
																										// actual
			Model model) {

		Pageable pageable = PageRequest.of(page, 15); // y esto me limitara los usuarios por pagina

		Page<Usuario> usuariosPage = usuarioService.filtrarUsuarios(nombre, documento, rol, estado, pageable);

		model.addAttribute("Usuarios", usuariosPage.getContent());
		model.addAttribute("page", usuariosPage);

		// model.addAttribute("Usuarios", usuarioService.findAll());
		model.addAttribute("nombre", nombre);
		model.addAttribute("documento", documento);
		model.addAttribute("rol", rol);
		model.addAttribute("estado", estado);

		return "Administrador/HistorialUSER";
	}

	// Editar usuario
	@GetMapping("/edicionUsuario/{id}")
	public String edicionUsuario(@PathVariable Integer id, Model model) {
		Usuario userEd = new Usuario(); // Objeto vacio
		Optional<Usuario> ud = usuarioService.get(id); // Llamas a tu servicio para buscar el usuario por su id.
		userEd = ud.get();
		LOGGER.warn("Busqueda de usuarios por id {}", userEd);
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas
		model.addAttribute("usuario", userEd);
		return "Administrador/EdicionUSER";

	}

	// Actualizar usuario
	@PostMapping("/updateUsuario")
	public String actualizarUsuario(Usuario usuarioForm, RedirectAttributes redirect) {
		LOGGER.info("Objeto recibido para actualizar: {}", usuarioForm);

		// Usuario de la BD
		Usuario usuarioDB = usuarioService.get(usuarioForm.getId())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (usuarioForm.getFicha() != null && usuarioForm.getFicha().getId() == null) {
			usuarioForm.setFicha(null);
		}
		// --- LÓGICA DE CONTRASEÑA ---
		if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isBlank()) {
			usuarioDB.setPassword(pe.encode(usuarioForm.getPassword()));
		}
		// Si viene vacía, NO tocamos la contraseña → se mantiene la actual

		usuarioDB.setNombre(usuarioForm.getNombre());
		usuarioDB.setDireccion(usuarioForm.getDireccion());
		usuarioDB.setDocumento(usuarioForm.getDocumento());
		usuarioDB.setTelefono(usuarioForm.getTelefono());
		usuarioDB.setEmail(usuarioForm.getEmail());
		usuarioDB.setFicha(usuarioForm.getFicha());
		usuarioDB.setRol(usuarioForm.getRol());

		usuarioService.update(usuarioDB);

		redirect.addFlashAttribute("mensaje", "Se ha editado la ficha con éxito");
		redirect.addFlashAttribute("tipo", "success");

		LOGGER.warn("Usuario actualizado: {}", usuarioDB);
		return "redirect:/Administrador/usuarios";
	}

	// Eliminacion de usuarios
	@GetMapping("/deleteUser/{id}")
	public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirect) {

		Usuario user = new Usuario();
		user = usuarioService.get(id).get();
		usuarioService.delete(id);

		redirect.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
		redirect.addFlashAttribute("tipo", "success");

		LOGGER.warn("Usuario eliminado {}", user);
		return "redirect:/Administrador/usuarios";
	}

	// ======================= FICHA ===========================

	// enlistar ficha
	@GetMapping("/Historialfichas")
	public String asignarCampoFichas(@RequestParam(required = false) String nombrePrograma,
			@RequestParam(required = false) String jornada, @RequestParam(required = false) String estado,
			@RequestParam(required = false) Integer numFicha, @RequestParam(defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, 10);

		Page<Ficha> fichaPage = fichaService.filtrarFicha(nombrePrograma, jornada, estado, numFicha, pageable);

		model.addAttribute("fichas", fichaPage.getContent());
		model.addAttribute("page", fichaPage);

		model.addAttribute("nombrePrograma", nombrePrograma);
		model.addAttribute("jornada", jornada);
		model.addAttribute("estado", estado);
		model.addAttribute("numFicha", numFicha);

		int totalPages = fichaPage.getTotalPages();
		int currentPage = fichaPage.getNumber();

		int maxPagesToShow = 10;

		// Rango visible centrado
		int startPage = Math.max(0, currentPage - maxPagesToShow / 2);
		int endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

		// Ajuste si estamos cerca del inicio
		if (startPage == 0) {
			endPage = Math.min(maxPagesToShow - 1, totalPages - 1);
		}

		// Ajuste si estamos al final
		if (endPage == totalPages - 1) {
			startPage = Math.max(0, endPage - maxPagesToShow + 1);
		}

		model.addAttribute("pageNumbers", java.util.stream.IntStream.rangeClosed(startPage, endPage).boxed().toList());

		model.addAttribute("lastPage", totalPages - 1);

		return "Administrador/HistorialFICHAS";
	}

	@GetMapping("/CambiarEstadoFicha/{id}")
	public String cambiarEstadoFicha(@PathVariable Integer id, RedirectAttributes redirect) {

		Ficha f = fichaService.get(id).orElse(null);
		if (f != null) {
			if ("Activo".equalsIgnoreCase(f.getEstado())) {
				f.setEstado("Inactivo");
			} else {
				f.setEstado("Activo");
			}
		}

		redirect.addFlashAttribute("mensaje", "Estado actualizado");
		redirect.addFlashAttribute("tipo", "success");

		fichaService.save(f);

		return "redirect:/Administrador/Historialfichas"; // Cambia esto a tu ruta real
	}

	@GetMapping("/GuardarFicha")
	public String GuardarFicha(Ficha ficha) {

		return "Administrador/RegistroFICHA";
	}

	// Guardar ficha
	@PostMapping("/fichaSave")
	public String guardarFicha(Ficha ficha, RedirectAttributes redirect) {

		ficha.setEstado("Activo");

		if (ficha.getJornada() != null && ficha.getJornada().isBlank()) {
			ficha.setJornada(null);
		}

		fichaService.save(ficha);

		redirect.addFlashAttribute("mensaje", "Ficha registrada correctamente");
		redirect.addFlashAttribute("tipo", "success");

		LOGGER.debug("La ficha se ha registrado con exito {}", ficha);

		return "redirect:/Administrador/Historialfichas";
	}

	@GetMapping("/ficha/{id}")
	public String verFicha(@PathVariable Integer id, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nombre,
			@RequestParam(required = false) String documento, @RequestParam(required = false) String rol,
			@RequestParam(required = false) String estado, Model model) {

		Pageable pageable = PageRequest.of(page, size);

		// Si alguno está lleno, aplicar filtros
		boolean hayFiltros = (nombre != null && !nombre.isEmpty()) || (documento != null && !documento.isEmpty())
				|| (rol != null && !rol.isEmpty()) || (estado != null && !estado.isEmpty());

		Page<Usuario> usuariosPage;

		if (hayFiltros) {
			usuariosPage = usuarioService.filtrarUsuariosEnFicha(id, nombre, documento, rol, estado, pageable);
		} else {
			usuariosPage = usuarioService.findByFichaIdPaginado(id, pageable);
		}

		Ficha ficha = fichaService.get(id).orElse(null);
		if (ficha == null) {
			return "redirect:/Administrador/Historialfichas";
		}

		model.addAttribute("ficha", ficha);
		model.addAttribute("usuariosPage", usuariosPage);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", usuariosPage.getTotalPages());
		model.addAttribute("totalItems", usuariosPage.getTotalElements());

		// Para mantener filtros en el modal
		model.addAttribute("nombre", nombre);
		model.addAttribute("documento", documento);
		model.addAttribute("rol", rol);
		model.addAttribute("estado", estado);

		return "Administrador/DetallesFicha";
	}

	// Editar ficha
	@GetMapping("/EdicionFichas/{id}")
	public String edicionFicha(@PathVariable Integer id, Model model) {

		Ficha ut = fichaService.get(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		LOGGER.warn("Busqueda de fichas por id {}", ut);
		model.addAttribute("ficha", ut);

		return "Administrador/EdicionFICHA";
	}

	// Actualizar ficha
	@PostMapping("/update")
	public String actualizarFicha(Ficha ficha, RedirectAttributes redirect) {
		LOGGER.info("Este es el objeto del usuario a actualizar en la DB {}", ficha);

		Ficha fichaAc = fichaService.get(ficha.getId()).get();

		fichaAc.setNombrePrograma(ficha.getNombrePrograma());
		fichaAc.setJornada(ficha.getJornada());
		fichaAc.setNumFicha(ficha.getNumFicha());

		fichaService.update(fichaAc);

		redirect.addFlashAttribute("mensaje", "Se ha editado la ficha con éxito");
		redirect.addFlashAttribute("tipo", "success");

		LOGGER.warn("Ficha actualizada: {}", fichaAc);

		return "redirect:/Administrador/Historialfichas";
	}

	// Eliminar ficha
	@GetMapping("/delete/{id}")
	public String eliminarFicha(@PathVariable Integer id, Integer numFicha, RedirectAttributes redirect) {

		// 1. Buscar los usuarios que tienen esta ficha
		List<Usuario> usuarios = usuarioService.findByFichaId(id);

		// 2. Ponerles ficha = null
		for (Usuario u : usuarios) {
			u.setFicha(null);
		}

		usuarioService.saveAll(usuarios);

		fichaService.delete(id);

		redirect.addFlashAttribute("mensaje", "Ficha eliminada");
		redirect.addFlashAttribute("tipo", "success");

		LOGGER.warn("Ficha eliminada: {}", id);
		return "redirect:/Administrador/Historialfichas";

	}

	// =============== PERFIL DE USUARIO - ADMIN ===============

	@GetMapping("/Perfil")
	private String Perfil() {
		return "Administrador/VerPerfil";
	}

	@GetMapping("/EdicionPerfil")
	public String edicionPerfil(HttpSession session, Model model) {

		Integer idAdmin = (Integer) session.getAttribute("IdUser");

		Optional<Usuario> admin = usuarioService.get(idAdmin);

		admin.ifPresent(a -> model.addAttribute("Admin", a));

		return "Administrador/EditPerfil";
	}

	@PostMapping("/NuevoPerfil")
	public String nuevoPerfil(@ModelAttribute Usuario usuario, RedirectAttributes redirect){

		Usuario u = new Usuario();
		u = usuarioService.get(usuario.getId()).get();

		
		Integer idAdmin = (Integer) session.getAttribute("IdUser");

		Optional<Usuario> PA = usuarioService.get(idAdmin);

		if (PA.isPresent()) {

			Usuario admin = PA.get();

			admin.setNombre(usuario.getNombre());
			admin.setEmail(usuario.getEmail());
			admin.setDireccion(usuario.getDireccion());
			admin.setDocumento(usuario.getDocumento());
			admin.setTelefono(usuario.getTelefono());

			usuarioService.save(admin);

			session.setAttribute("nombre", admin.getNombre());
			session.setAttribute("email", admin.getEmail());
			session.setAttribute("direccion", admin.getDireccion());
			session.setAttribute("documento", admin.getDocumento());
			session.setAttribute("telefono", admin.getTelefono());
		}

		redirect.addFlashAttribute("mensaje", "Perfil actualizado");
		redirect.addFlashAttribute("tipo", "success");

		return "redirect:/Administrador/Perfil";
	}
}
