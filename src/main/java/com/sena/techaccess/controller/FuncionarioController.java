package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/dashboard")
	public String mostrarDashboard(Model model) {
		Usuario user = usuarioService.findAll().get(1);
		model.addAttribute("rol", user.getRol());
		model.addAttribute("nombre", user.getNombre());

		return "Funcionarios/Dashboard";
	}
}
