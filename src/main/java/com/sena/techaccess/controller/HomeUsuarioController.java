package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.ISoporteService;
import com.sena.techaccess.model.Soporte;

@Controller
//@RequestMapping("/")
public class HomeUsuarioController {

	@Autowired
	private IAccesoService accesoService;

	@GetMapping("/soporte")
	public String soporte() {
		return "soporte";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/Home") //antes estaba solo ""
	public String Inicio() {
		return "home";
	}
	
	@GetMapping("/")
	public String raiz() {
		return "redirect:/Home";
	}

}