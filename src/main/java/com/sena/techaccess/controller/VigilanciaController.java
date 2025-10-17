package com.sena.techaccess.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.DispositivoVisitRepository;
import com.sena.techaccess.service.IDispositivoService;
import com.sena.techaccess.service.IDispositivoVisitService;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.IVigilanciaService;

@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private IDispositivoService dispositivoService;

	@Autowired
	private IVigilanciaService vigilanciaService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IDispositivoVisitService dispositivoVisitService;

	@GetMapping("/Ingreso")
	public String usuariosvigilancia(Model model) {

		model.addAttribute("Usuarios", usuarioService.findAll()); // Listado de usuarios
		model.addAttribute("usuario", new Usuario());

		return "Vigilancia/Ingresos";

	}

	// ====================== VISITANTES ===============================

	// Registro de visitantes
	@GetMapping("/registro")
	public String historialVisitante(Usuario usuario, Model model) {

		model.addAttribute("usuario", new Usuario());
	    model.addAttribute("dispositivo", new DispositivoVisit());
	    
	    // Traer todos los usuarios que tengan rol Visitante
	    List<Usuario> visitantes = usuarioService.findByRol("Visitante");
	    model.addAttribute("visitantesH", visitantes);

		LOGGER.warn("Visitante en historial: {}", visitantes);

		return "Vigilancia/Visitantes";
	}

	// Guardar visitantes
	@PostMapping("/visitanteSave")
	public String registroVisitante(Usuario usuario, DispositivoVisit dispositivo, Model model) {

		// Asignar rol a los visitantes
		usuario.setRol("Visitante");

		usuarioService.save(usuario);

		// Verificar si viene info de dispositivo (para no guardar uno vacío)
		if (dispositivo.getTipo() != null && !dispositivo.getTipo().equals("--Tipo--")) {
			// Asociar el usuario recién guardado al dispositivo
			dispositivo.setUsuario(usuario);
			dispositivoVisitService.save(dispositivo);
			LOGGER.debug("Dispositivo registrado para el visitante: {}", dispositivo);
		}

		LOGGER.debug("Visitante agregado con éxito: {}", usuario);

		return "redirect:/Vigilancia/registro";
	}
	// ====================== DISPOSITIVOS Visitantes
	// ===============================

	// Lista de dispositivos de visitantes
	@GetMapping("/DispositivosVisit")
	public String dispositivosUser(DispositivoVisit dispositivo, Model model) {

		model.addAttribute("dispositivosVisit", dispositivoVisitService.findAll());

		return "Vigilancia/Dispositivos";
	}

	@PostMapping("/DeleteDV/{id}")
	public String EliminarDispoV(@PathVariable Integer id) {

		DispositivoVisit DispoVisitDl = new DispositivoVisit();
		DispoVisitDl = dispositivoVisitService.get(id).get();
		dispositivoVisitService.delete(id);
		LOGGER.warn("Dispositivo de visitante eliminado; {}", DispoVisitDl);

		return "redirect:/Vigilancia/DispositivoVisit";
	}
}
