package com.sena.techaccess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.repository.SoporteRepository;
import com.sena.techaccess.service.ISoporteService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UsuarioINController.class);

	@Autowired
	private ISoporteService soporteService;

	@Autowired
	private SoporteRepository soporteRepository;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("accesos", soporteService.findAll());
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

	// ==============================================================

	@GetMapping("/Suport")
	private String Soporteform(Model model) {

		model.addAttribute("Soportes", soporteRepository.findAll());
		model.addAttribute("Ssoporte", new Soporte());

		return "usuario/home";

	}

	@PostMapping("/SuportSave")
	private String SoporteSave(Soporte soporte) {

		soporteRepository.save(soporte);
		LOGGER.debug("Mensaje enviado con exito {}", soporte);

		return "redirect:/usuario/soporte";
	}

}