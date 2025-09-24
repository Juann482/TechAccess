package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.IVigilanciaService;


@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {
	
	@Autowired
	private IVigilanciaService vigilanciaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/Inicio") 
	public String usuariosvigilnancia(Model model) {
		
		model.addAttribute("Usuarios", usuarioService.findAll()); // Listado de usuarios
		model.addAttribute("usuario", new Usuario());
		
		return "Vigilancia/Ingresos";

	}

}
