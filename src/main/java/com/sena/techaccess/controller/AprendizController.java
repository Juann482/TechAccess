package com.sena.techaccess.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.service.IEstadoCuentaService;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	private final Logger LOGGER = LoggerFactory.getLogger(AprendizController.class);

	@Autowired
	private IExcusasService excusasService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;

	@Autowired
	private FichaRepository fichaRepository;

	@Autowired
	private IEstadoCuentaService estadocuentaService;

	AprendizController(FichaRepository fichaRepository) {
		this.fichaRepository = fichaRepository;
	}

	@GetMapping("/aprendiz")
	public String InicioAprendiz() {
		return "Aprendiz/aprendiz";
	}

	@GetMapping("/excusas")
	public String mostarexcusas(Model modelo) {
		modelo.addAttribute("Excusas", new Excusas());
		return "Aprendiz/excusas";
	}

	// =========================== Pagina principal
	// ===================================

	@GetMapping("/infoUser")
	public String mostrar(Model model) {

		model.addAttribute("usuario", usuarioService);
		model.addAttribute("excusas", excusasService.findAll());
		model.addAttribute("estadoCuenta", estadocuentaService.findAll());


		return "Aprendiz/aprendiz"; // ✅ devuelve la vista
	}
	
	@GetMapping("/lista")
	public String fichas(Model model) {
		
		model.addAttribute("fichas", fichaService.findAll());
		
		return "Aprendiz/excusas";
		
	}

	@PostMapping("/lista")
	public String Fichas(Excusas excusas) {

		excusasService.save(excusas);
		LOGGER.warn("Excusa registrada con exito: {}", excusas);

		return "Aprendiz/excusas";
	}

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
		return "Aprendiz/excusas";
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

	@PostMapping("/datosUser")
	public String guardarExcusa(@RequestParam("Excusas") Excusas excusas) throws IOException {
		if (excusas.getSoporteFile() != null && !excusas.getSoporteFile().isEmpty()) {
			// Guardar solo el nombre del archivo en el campo soporte (String)
			excusas.setSoporte(excusas.getSoporteFile().getOriginalFilename());

			// Crear carpeta uploads si no existe
			Path directorio = Paths.get("uploads");
			if (!Files.exists(directorio)) {
				Files.createDirectories(directorio);
			}

			// Guardar físicamente el archivo
			Path ruta = directorio.resolve(excusas.getSoporteFile().getOriginalFilename());
			Files.write(ruta, excusas.getSoporteFile().getBytes());
		}

		excusasService.save(excusas);
		return "redirect:/Aprendiz/excusas";
	}

	// @GetMapping("/list")
	// public String listarFichas(Model model) {
	// model.addAttribute("fichas", fichaRepository.findAll());
	// return "Aprendiz/listaFichas";

}
