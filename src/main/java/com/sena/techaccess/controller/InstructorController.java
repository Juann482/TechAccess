package com.sena.techaccess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.sena.techaccess.model.Acceso;
//import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.repository.AccesoRepository;
//import com.sena.techaccess.service.AccesoServiceImplement;
//import com.sena.techaccess.service.FichaServiceImplement;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

	@Autowired
	private AccesoRepository accesoRepository;

	@GetMapping("/home")
	public String home(Model model) {
		List<Acceso> accesos = accesoRepository.findAll();
		model.addAttribute("acceso", accesos);
		return "instructor";
	}

	@GetMapping("/instructor")
	public String vistaInstructor(Model model) {
		List<Acceso> accesos = accesoRepository.findAll();
		model.addAttribute("accesos", accesos);
		return "instructor";
	}

}

/*
 * @Controller //@RequestMapping("/instructor") public class
 * InstructorController {
 * 
 * @Autowired private AccesoServiceImplement accesoService;
 * 
 * @Autowired private FichaServiceImplement fichaService;
 * 
 * @GetMapping("/instructor") public String vistaInstructor(Model model) {
 * 
 * Fichas para tpl-grupos List<Ficha> fichas = fichaService.findAll();
 * model.addAttribute("fichas", fichas);
 * 
 * Últimos accesos para tpl-acceso List<Acceso> accesos =
 * accesoService.findAll(); model.addAttribute("acceso", accesos);
 * 
 * return "Usuario_Interno/instructor"; }
 * 
 * 
 * @GetMapping("/grupos") public String verGrupos(Model model) { List<Ficha>
 * fichas = fichaService.findAll(); model.addAttribute("fichas", fichas); return
 * "Usuario_Interno/instructor"; }
 */
