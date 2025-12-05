package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.techaccess.model.Soporte;
import com.sena.techaccess.service.ISoporteService;

import jakarta.validation.Valid;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class SoporteController {

	@Autowired
	private ISoporteService soporteService;

	// Método para mostrar el formulario
	@GetMapping
	public String mostrarFormulario(Model model) {
		model.addAttribute("soporte", new Soporte());
		return "soporte"; // Retorna a tu HTML
	}

	// Método para procesar el formulario
	@PostMapping("/save")
	public String guardarSoporte(@ModelAttribute Soporte soporte, Model model) {
		soporteService.save(soporte);
		model.addAttribute("mensaje", "¡Mensaje enviado exitosamente!");
		return "redirect:/soporte?exito=true";
	}

	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verSoporte(@PathVariable String filename) {
		try {
			Path archivo = Paths.get("uploads").resolve(filename).toAbsolutePath();
			Resource recurso = new UrlResource(archivo.toUri());

			if (!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo " + filename);
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
					.body(recurso);

		} catch (MalformedURLException e) {
			throw new RuntimeException("Error al cargar el archivo: " + filename, e);
		}

	}

	@PostMapping("/save")
	public String guardarSoporte(@Valid @ModelAttribute Soporte soporte, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "soporte"; // Vuelve al formulario con errores
		}

		soporteService.save(soporte);
		return "redirect:/soporte?exito=true";
	}
}
