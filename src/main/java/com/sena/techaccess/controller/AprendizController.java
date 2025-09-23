package com.sena.techaccess.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IExcusasService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	private final Logger LOGGER = LoggerFactory.getLogger(AprendizController.class);

	@Autowired
	private IExcusasService excusasService;

	@GetMapping("/aprendiz")
	public String mostrarAprendiz(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		model.addAttribute("usuario", usuario);
		model.addAttribute("excusa", new Excusas());

		return "Aprendiz/aprendiz";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Excusas excusa, @RequestParam("file") MultipartFile file) throws IOException {
		LOGGER.info("Objeto recibido: {}", excusa);

		if (!file.isEmpty()) {
			String nombreArchivo = file.getOriginalFilename();
			excusa.setSoporte(nombreArchivo);
			// aquí puedes implementar mover archivo a carpeta uploads
		}

		excusasService.save(excusa);
		return "redirect:/Aprendiz/aprendiz"; // ✅ ruta existente
	}

}