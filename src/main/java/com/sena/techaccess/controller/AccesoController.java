package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.techaccess.service.IAccesoService;

public class AccesoController {

	@Autowired
	private IAccesoService accesoService;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("accesos", accesoService.findAll());
		return "usuario/home";
	}
}