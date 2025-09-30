package com.sena.techaccess.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Rol;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.RolRepository;
import com.sena.techaccess.service.AccesoServiceImplement;
import com.sena.techaccess.service.FichaServiceImplement;
import com.sena.techaccess.service.IRolService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Instructor")
public class InstructorController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private AccesoServiceImplement accesoService;

	@Autowired
	private FichaServiceImplement fichaService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private IRolService rolService;

	@GetMapping("/instructor")
		private String ultimasEntradas(Model model) {
		
		Rol aprendiz = rolRepository.findByTipo("Aprendiz");
		
		List<Usuario> aprendices = usuarioService.findByRol(aprendiz);
		if (aprendices.isEmpty()) {
			LOGGER.error("No hay aprendices ingresados"); 
		}
		model.addAttribute("Aprendices", aprendices);
		LOGGER.warn("Aprendiz en historial: {}", aprendices);
		
		return "Instructor/Entradas";
	}

	/*
	 * @GetMapping("/instrr") public String vistaInstructor(Model model) {
	 * 
	 * // Fichas para tpl-grupos List<Ficha> fichas = fichaService.findAll();
	 * model.addAttribute("fichas", fichas);
	 * 
	 * // Últimos accesos para tpl-acceso List<Acceso> accesos =
	 * accesoService.findAll(); model.addAttribute("acceso", accesos);
	 * 
	 * return "Usuario_Interno/instructor"; }
	 */

	/*
	 * @GetMapping("/grupos") public String verGrupos(Model model) { List<Ficha>
	 * fichas = fichaService.findAll(); model.addAttribute("fichas", fichas); return
	 * "Usuario_Interno/instructor"; }
	 */

}