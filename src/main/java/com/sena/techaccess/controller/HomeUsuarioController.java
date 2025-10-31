package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.IUsuarioService;

@Controller
public class HomeUsuarioController {

	// private final Logger LOGGER = (Logger)
	// LoggerFactory.getLogger(HomeUsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/")
	public String Inicio() {
		return "/home";
	}
	
	@GetMapping("/soporte")
	public String soporte() {
		return "/soporte"; // busca templates/usuario/soporte.html
	}

	@GetMapping("/about")
	public String about() {
		return "/about";
	}

}
