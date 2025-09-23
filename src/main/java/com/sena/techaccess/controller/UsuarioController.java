package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.UsuarioRepository;
import com.sena.techaccess.service.IAccesoService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IAccesoService accesoService;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("accesos", accesoService.findAll());
		return "usuario/home";
	}

	@GetMapping("/soporte")
	public String soporte() {
		return "usuario/soporte"; // busca templates/usuario/soporte.html
	}

	@GetMapping("/about")
	public String about() {
		return "usuario/about";
	}

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/Aprendiz/aprendiz")
	public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
		usuarioRepository.save(usuario);
		model.addAttribute("usuario", usuario);
		return "usuario_Interno/Aprendiz/aprendiz";
	}
}