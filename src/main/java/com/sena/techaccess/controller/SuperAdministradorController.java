package com.sena.techaccess.controller;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.SuperAdministrador;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.SuperAdministradorServiceImplement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Superadmin")
public class SuperAdministradorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuperAdministradorController.class);

	private final IAccesoService accesoService;

	public SuperAdministradorController(IAccesoService accesoService) {
		this.accesoService = accesoService;
	}

	@GetMapping("")
	public String home(Model model) {
		List<Acceso> accesos = accesoService.findAll();
		LOGGER.info("Cargando accesos en administrador/dashboard. Total: {}", accesos.size());
		model.addAttribute("accesos", accesos);
		return "SuperAdmin/superadmin";
	}

	
	 @GetMapping("/Iniciar") public String dashboard(Model model) {
	 model.addAttribute("acceso", accesoService.findAll()); return
	 "SuperAdmin/superadmin"; }
	 

	@Autowired
	private SuperAdministradorServiceImplement superAdministradorService;

	/*@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("currentPage", "Dashboard");
		return "superadmin/dashboard";
	}*/

	@GetMapping("/usuarios")
	public String gestionUsuarios(Model model) {
		model.addAttribute("currentPage", "Gestión de Usuarios");
		return "superadmin/usuarios";
	}

	@GetMapping("/superadmins")
	public String listarSuperAdmins(Model model) {
		List<SuperAdministrador> superAdmins = superAdministradorService.findAll();
		model.addAttribute("superAdmins", superAdmins);
		model.addAttribute("currentPage", "SuperAdministradores");
		return "superadmin/lista";
	}

	@GetMapping("/superadmins/nuevo")
	public String mostrarFormularioNuevo(Model model) {
		model.addAttribute("superAdmin", new SuperAdministrador());
		model.addAttribute("currentPage", "Nuevo SuperAdmin");
		return "superadmin/formulario";
	}

	@PostMapping("/superadmins/guardar")
	public String guardarSuperAdmin(@ModelAttribute SuperAdministrador superAdministrador) {
		superAdministradorService.save(superAdministrador);
		return "redirect:/superadmin/superadmins";
	}

	@GetMapping("/superadmins/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
		Optional<SuperAdministrador> superAdmin = superAdministradorService.findById(id);
		if (superAdmin.isPresent()) {
			model.addAttribute("superAdmin", superAdmin.get());
			model.addAttribute("currentPage", "Editar SuperAdmin");
			return "superadmin/formulario";
		}
		return "redirect:/superadmin/superadmins";
	}

	@GetMapping("/superadmins/eliminar/{id}")
	public String eliminarSuperAdmin(@PathVariable Integer id) {
		superAdministradorService.deleteById(id);
		return "redirect:/superadmin/superadmins";
	}

	@GetMapping("/vigilancia")
	public String gestionVigilancia(Model model) {
		model.addAttribute("currentPage", "Gestión de Vigilancia");
		return "superadmin/vigilancia";
	}

	@GetMapping("/accesos")
	public String gestionAccesos(Model model) {
		model.addAttribute("currentPage", "Gestión de Accesos");
		return "superadmin/accesos";
	}

	@GetMapping("/configuracion")
	public String configuracionSistema(Model model) {
		model.addAttribute("currentPage", "Configuración del Sistema");
		return "superadmin/configuracion";
	}
}