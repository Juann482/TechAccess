package com.sena.techaccess.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Rol;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.RolRepository;
import com.sena.techaccess.repository.UsuarioRepository;
import com.sena.techaccess.service.IRolService;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.IVigilanciaService;


@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {
	
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);
	
	@Autowired
	private IVigilanciaService vigilanciaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private RolRepository rolRepository;
	
	@GetMapping("/Ingreso") 
	public String usuariosvigilancia(Model model) {
		
		model.addAttribute("Usuarios", usuarioService.findAll()); // Listado de usuarios
		model.addAttribute("usuario", new Usuario());
		
		return "Vigilancia/Ingresos";

	}
	
	//====================== VISITANTES ===============================
	
	//Registro de visitantes
	@GetMapping("/registro")
	public String historialVisitante( Model model) {
		
		Rol rolVisitante = rolRepository.findByTipo("Visitante");
		//Traigo a los usuarios que son compatibles con ese rol
		List<Usuario> visitantes = usuarioService.findByRol(rolVisitante);
		if (visitantes.isEmpty()) {
	        LOGGER.error("No hay visitantes registrados");
	    }
		 model.addAttribute("visitantesH", visitantes);
		 LOGGER.warn("Visitante en historial: {}", visitantes);
		
		return "Vigilancia/Visitantes";
	}
	
	//Guardar visitantes
	@PostMapping("/visitanteSave")
	public String registroVisitante(Usuario usuario) {
		
		//Asignar rol a los visitantes
		Rol rolVisitante = rolRepository.findByTipo("Visitante");
		usuario.setRol(rolVisitante);
		
		usuarioService.save(usuario);
		LOGGER.debug("Visitante agregado con exito {}",usuario);		
		
		return "redirect:/Vigilancia/registro";
	}
	//====================== Dispositivos ===============================

}
