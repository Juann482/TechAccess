package com.sena.techaccess.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.service.IExcusasService;

@Controller
@RequestMapping("/Excusas")
public class ExcusasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcusasController.class);

	@Autowired
	private IExcusasService excusasService;

	// Formulario vacío
	@GetMapping("/form")
	public String mostrarFormulario(Model model) {
		model.addAttribute("excusa", new Excusas());
		return "Aprendiz/excusaForm";
	}

	// Guardar nueva excusa
	@PostMapping("/save")
	public String guardarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
			throws IOException {
		LOGGER.info("Excusa recibida: {}", excusa);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
			Path ruta = Paths.get("uploads").resolve(nombreArchivo);
			Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
			excusa.setSoporte(nombreArchivo);
		}

		excusasService.save(excusa);
		LOGGER.info("Excusa guardada con ID {}", excusa.getId());

		return "redirect:/Excusas/list";
	}

	// Listado
	@GetMapping("/list")
	public String listarExcusas(Model model) {
		model.addAttribute("excusas", excusasService.findAll());
		return "Aprendiz/listaExcusas";
	}

	// Editar
	@GetMapping("/edit/{id}")
	public String editarExcusa(@PathVariable Integer id, Model model) {
		Excusas excusa = excusasService.findById(id).orElseThrow(() -> new RuntimeException("Excusa no encontrada"));
		model.addAttribute("excusa", excusa);
		return "Aprendiz/excusaForm";
	}

	// Actualizar
	@PostMapping("/update")
	public String actualizarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
			throws IOException {

		Excusas existente = excusasService.findById(excusa.getId())
				.orElseThrow(() -> new RuntimeException("Excusa no encontrada"));

		existente.setNombres(excusa.getNombres());
		existente.setNdocumento(excusa.getNdocumento());
		existente.setFicha(excusa.getFicha());
		existente.setNumeroFicha(excusa.getNumeroFicha());
		existente.setMotivo(excusa.getMotivo());
		existente.setFecha(excusa.getFecha());

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
			Path ruta = Paths.get("uploads").resolve(nombreArchivo);
			Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
			existente.setSoporte(nombreArchivo);
		}

		excusasService.save(existente);
		LOGGER.info("Excusa actualizada ID {}", existente.getId());

		return "redirect:/Excusas/list";
	}

	// Eliminar
	@GetMapping("/delete/{id}")
	public String eliminarExcusa(@PathVariable Integer id) {
		excusasService.delete(id);
		LOGGER.info("Excusa eliminada ID {}", id);
		return "redirect:/Excusas/list";
	}
}
