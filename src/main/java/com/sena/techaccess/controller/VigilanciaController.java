package com.sena.techaccess.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IDispositivoService;
import com.sena.techaccess.service.IDispositivoVisitService;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.IVigilanciaService;

@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {

	private final Logger LOGGER = LoggerFactory.getLogger(VigilanciaController.class);

	@Autowired
	private IDispositivoService dispositivoService;

	@Autowired
	private IVigilanciaService vigilanciaService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IAccesoService accesoService;

	@Autowired
	private IDispositivoVisitService dispositivoVisitService;

	// ======================= INGRESO ===================================== //

	@GetMapping("/Ingreso")
	public String usuariosvigilancia(Model model) {
		List<Usuario> usuarios = usuarioService.findAll();
		Map<Integer, Acceso> ultimosAccesos = accesoService.findLatestAccessForAllUsers();

		// Asignar el último acceso a cada usuario
		for (Usuario usuario : usuarios) {
			if (usuario != null && ultimosAccesos != null) {
				usuario.setAcceso(ultimosAccesos.get(usuario.getId()));
			}
		}

		model.addAttribute("Usuarios", usuarios);
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("Acceso", new Acceso());

		return "Vigilancia/Ingresos";
	}

	/**
	 * Registrar ingreso con validación de un ingreso activo
	 */
	@PostMapping("/registrar-ingreso")
	@ResponseBody
	public String registrarIngreso(@RequestParam("documento") String documento) {

		Usuario usuario = usuarioService.findByDocumento(documento);

		if (usuario == null) {
			LOGGER.warn("Usuario no encontrado para el documento {}", documento);
			return "Usuario no encontrado";
		}

		// Buscar último acceso
		Acceso ultimoAcceso = accesoService.findUltimoAcceso(usuario.getId());

		// Si el último acceso no tiene hora de salida → todavía está dentro
		if (ultimoAcceso != null && ultimoAcceso.getHoraEgreso() == null) {
			return "El usuario ya tiene un ingreso activo. Debe registrar salida.";
		}

		// Crear nuevo registro de acceso
		Acceso nuevo = new Acceso();
		nuevo.setUsuario(usuario);
		nuevo.setHoraIngreso(LocalDateTime.now());

		accesoService.save(nuevo);

		LOGGER.info("Ingreso registrado -> Usuario {}", usuario.getNombre());

		return "Ingreso registrado correctamente";
	}

	/**
	 * Registrar salida del usuario
	 */
	@PostMapping("/registrar-egreso")
	@ResponseBody
	public String registrarEgreso(@RequestParam("documento") String documento) {

		Usuario usuario = usuarioService.findByDocumento(documento);

		if (usuario == null) {
			return "Usuario no encontrado";
		}

		// Buscar último acceso sin salida
		Acceso ultimoAcceso = accesoService.findbyHoraIngreso(usuario.getId());

		if (ultimoAcceso == null || ultimoAcceso.getHoraEgreso() != null) {
			return "No hay ingreso activo para este usuario.";
		}

		ultimoAcceso.setHoraEgreso(LocalDateTime.now());
		accesoService.save(ultimoAcceso);

		LOGGER.info("Egreso registrado -> Usuario {}", usuario.getNombre());

		return "Egreso registrado correctamente";
	}

	// ====================== VISITANTES ================================= //

	@GetMapping("/registro")
	public String historialVisitante(Usuario usuario, Model model) {

		model.addAttribute("usuario", new Usuario());
		model.addAttribute("dispositivo", new DispositivoVisit());

		List<Usuario> visitantes = usuarioService.findByRol("Visitante");
		model.addAttribute("visitantesH", visitantes);

		LOGGER.debug("Visitantes cargados {}", visitantes);

		return "Vigilancia/Visitantes";
	}

	@PostMapping("/visitanteSave")
	public String registroVisitante(Usuario usuario, DispositivoVisit dispositivo) {

		usuario.setRol("Visitante");
		usuarioService.save(usuario);

		if (dispositivo.getTipo() != null && !dispositivo.getTipo().equals("--Tipo--")) {
			dispositivo.setUsuario(usuario);
			dispositivoVisitService.save(dispositivo);

			LOGGER.debug("Dispositivo registrado para visitante {}", dispositivo);
		}

		return "redirect:/Vigilancia/registro";
	}

	// ================== DISPOSITIVOS VISITANTES ===========================

	@GetMapping("/DispositivosVisit")
	public String dispositivosUser(Model model) {

		model.addAttribute("dispositivosVisit", dispositivoVisitService.findAll());
		model.addAttribute("dispoVisit", new DispositivoVisit());

		return "Vigilancia/Dispositivos";
	}

	@GetMapping("/DeleteDV/{id}")
	public String EliminarDispoV(@PathVariable Integer id) {

		dispositivoVisitService.delete(id);
		LOGGER.warn("Dispositivo Visitante eliminado: {}", id);

		return "redirect:/Vigilancia/DispositivosVisit";
	}

	// ================== DISPOSITIVOS FUNCIONARIOS ===========================

	@GetMapping("/DispositivosUSER")
	public String dispositivosUSER(Model model) {

		model.addAttribute("DispoUSER", new Usuario());
		return "Vigilancia/RegistroDU";
	}

	@PostMapping("/GuardarDispoUSER")
	public String guardarDispositivoU(Dispositivo dispositivo) {

		dispositivoService.save(dispositivo);
		LOGGER.debug("Dispositivo funcionario guardado {}", dispositivo);

		return "redirect:/Vigilancia/Ingreso";
	}

	// ======================== REGISTRO DISPOSITIVO ==========================

	@GetMapping("/RDU")
	public String ListadoDispoUSER(Model model) {

		model.addAttribute("Listado", dispositivoService.findAll());
		model.addAttribute("ListadoDispo", new Dispositivo());

		return "Vigilancia/DispositivosUSER";
	}

	@GetMapping("/DeleteDUSER/{id}")
	public String EliminarDispoUSER(@PathVariable Integer id) {

		dispositivoService.delete(id);
		LOGGER.warn("Dispositivo funcionario eliminado {}", id);

		return "redirect:/Vigilancia/RDU";
	}
}
