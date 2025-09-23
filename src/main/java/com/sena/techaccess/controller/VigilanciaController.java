package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.IVigilanciaService;

@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {
	
	@Autowired
	private IVigilanciaService vigilanciaService;
	
	@GetMapping("/Inicio")
	private String InicioVigilancia() {
		return "Vigilancia/Inicio";
	}

}
