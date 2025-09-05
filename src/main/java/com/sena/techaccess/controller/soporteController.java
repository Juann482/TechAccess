package com.sena.techaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class soporteController {

	@GetMapping("/soporte")
	public String soporte() {

		return "soporte";
	}
}
