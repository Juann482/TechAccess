package com.sena.techaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeUsuarioController {

	// private final Logger LOGGER = (Logger)
	// LoggerFactory.getLogger(HomeUsuarioController.class);

	@GetMapping("")
	public String Inicio() {
		return ("redirect:/usuario/home");
	}
}
