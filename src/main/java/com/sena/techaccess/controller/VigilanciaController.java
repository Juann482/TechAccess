package com.sena.techaccess.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.DispositivoVisitRepository;
import com.sena.techaccess.service.IAccesoService;
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
	private IAccesoService accesoService;

	@Autowired
	private IDispositivoVisitService dispositivoVisitService;
	
	//======================= INGRESO ==========================//

	@GetMapping("/Ingreso")
	public String usuariosvigilancia(Model model) {

		model.addAttribute("Usuarios", usuarioService.findAll()); // Listado de usuarios
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("Acceso", new Acceso());

		return "Vigilancia/Ingresos";

	}
	
	@PostMapping("/registrar-ingreso")
	@ResponseBody
	public String registrarIngreso(@RequestParam("documento") String documento) {
	    Usuario usuario = usuarioService.findByDocumento(documento);

	    if (usuario == null) {
	        LOGGER.warn("Usuario no encontrado para el documento: {}", documento);
	        return "Usuario no encontrado";
	    }
	    

	    Acceso acceso = usuario.getAcceso();

	    // Si no tiene un acceso creado, se crea uno nuevo
	    if (acceso == null) {
	        acceso = new Acceso();
	        acceso.setUsuario(usuario);
	        usuario.setAcceso(acceso);
	    }

	    // Registrar hora de ingreso actual
	    acceso.setHoraIngreso(LocalDateTime.now());

	    usuarioService.save(usuario);

	    LOGGER.info("✅ Ingreso registrado para: {}", usuario.getNombre());
	    
	    return "Ingreso registrado correctamente";
	}


	// ====================== VISITANTES ===============================

	// Registro de visitantes
	@GetMapping("/registro")
	public String historialVisitante(Usuario usuario, Model model) { //El @ModelAttribute permite recibir los datos del formulario directamente.

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
	// ================== DISPOSITIVOS Visitantes ===========================

	// Lista de dispositivos de visitantes
	@GetMapping("/DispositivosVisit")
	public String dispositivosUser(DispositivoVisit dispositivo, Model model) {

		model.addAttribute("dispositivosVisit", dispositivoVisitService.findAll());
		model.addAttribute("dispoVisit", new DispositivoVisit());

		return "Vigilancia/Dispositivos";
	}

	@GetMapping("/DeleteDV/{id}")
	public String EliminarDispoV(@PathVariable Integer id) {

		DispositivoVisit DispoVisitDl = new DispositivoVisit();
		DispoVisitDl = dispositivoVisitService.get(id).get();
		dispositivoVisitService.delete(id);
		LOGGER.warn("Dispositivo de visitante eliminado; {}", DispoVisitDl);

		return "redirect:/Vigilancia/DispositivosVisit";
	}
	
	//================= DISPOSITIVOS Funcionarios ===========================
	
	@GetMapping("/DispositivosUSER")
	public String dispositivosUSER(Model model, Usuario usuario) {
		
		model.addAttribute("DispoUSER", new Usuario());
		
		return "Vigilancia/RegistroDU";
	}
	
	@PostMapping("/GuardarDispoUSER")
	public String guardarDispositivoU(Dispositivo dispositivo ) {
		
		dispositivoService.save(dispositivo);
		LOGGER.debug("Dispositivo guardado con exito: {}", dispositivo);
		
		return "redirect:/Vigilancia/Ingreso"; //Temporal
	}
	
	//========================= REGISTRO DISPOSITIVO ========================//
	
	@GetMapping("/RDU")
	public String ListadoDispoUSER(Dispositivo dispositivo, Model model) {
		
		model.addAttribute("Listado", dispositivoService.findAll());
		model.addAttribute("ListadoDispo", new Dispositivo());
		
		return "Vigilancia/DispositivosUSER";
	}
	
	@GetMapping("/DeleteDUSER/{id}")
	public String EliminarDispoUSER(@PathVariable Integer id) {

		DispositivoVisit DispoVisitDl = new DispositivoVisit();
		DispoVisitDl = dispositivoVisitService.get(id).get();
		dispositivoVisitService.delete(id);
		LOGGER.warn("Dispositivo de visitante eliminado; {}", DispoVisitDl);

		return "redirect:/Vigilancia/RDU";
	}
}
