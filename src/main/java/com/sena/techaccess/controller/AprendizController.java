package com.sena.techaccess.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.service.IEstadoCuentaService;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	// >private final Logger LOGGER =
	// LoggerFactory.getLogger(AprendizController.class);


	@Autowired
	private IExcusasService excusasService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;

	@Autowired
	private IEstadoCuentaService estadocuentaService;

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

	@GetMapping("/excusas/datosUser")
	public String mostrar(Model model) {
		Excusas excusas = new Excusas();

		// Suponiendo que findAll() devuelve al menos un usuario
		var usuario = usuarioService.findAll().get(0);

		model.addAttribute("usuario", excusasService);
		model.addAttribute("Excusas", excusasService.findAll());
		model.addAttribute("numFicha", excusasService.findAll().get(0));
		model.addAttribute("estadoCuenta", estadocuentaService.findAll().get(0));
		return"";

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

	@GetMapping("/Excusas")
	public String listarExcusas(Model model) {
		model.addAttribute("excusas", excusasService.findAll());
		return "Aprendiz/excusas"; // tu nueva vista con la tabla

	}

}