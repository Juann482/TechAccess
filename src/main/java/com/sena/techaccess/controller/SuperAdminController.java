package com.sena.techaccess.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.service.IAccesoService;

@Controller
@RequestMapping("/administrador")

public class SuperAdminController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AccesoController.class);

	@Autowired
	private IAccesoService accesoService;

	@GetMapping("")
	public String home(Model model) {
		List<Acceso> accesos = accesoService.findAll();
		model.addAttribute("acceso", accesos);
		return "administrador/home";
	}
}