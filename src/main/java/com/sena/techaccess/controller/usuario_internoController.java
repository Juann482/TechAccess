package com.sena.techaccess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario_interno")
public class usuario_internoController {

	@GetMapping("/aprendiz")
	public String aprendiz(Model model) {
		model.addAttribute("fechaVAlidez", "31/12/2025");
		return "usuario_interno/aprendiz";
	}
}
