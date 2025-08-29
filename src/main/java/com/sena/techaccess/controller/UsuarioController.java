package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.IAccesoService;


@Controller
@RequestMapping("/")
public class UsuarioController {


	@Autowired
	private IAccesoService accesoService;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("accesos", accesoService.findAll());
		return "usuario/home";
	}
}