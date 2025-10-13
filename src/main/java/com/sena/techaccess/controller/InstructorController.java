package com.sena.techaccess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.repository.AccesoRepository;
import com.sena.techaccess.repository.FichaRepository;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

	@Autowired
	private AccesoRepository accesoRepository;

	@Autowired
	private FichaRepository fichaRepository;

	@GetMapping("")
	public String vistaPrincipal(Model model) {
		List<Acceso> accesos = accesoRepository.findAll();
		model.addAttribute("accesos", accesos);
		return "instructor/instructor";
	}

	@GetMapping("/nvasEntradas")
	public String vistaEntradas(Model model) {
		try {
			List<Acceso> accesos = accesoRepository.findAll();

			System.out.println("=== DEBUG NUEVAS ENTRADAS ===");
			System.out.println("Número de accesos encontrados: " + accesos.size());

			// mostrar info del primer acceso de existir
			if (!accesos.isEmpty()) {
				Acceso primerAcceso = accesos.get(0);
				System.out.println("Primer acceso - ID: " + primerAcceso.getIdacceso());
				System.out.println("Usuario: "
						+ (primerAcceso.getUsuario() != null ? primerAcceso.getUsuario().getNombre() : "NULL"));
			}

			if (accesos.isEmpty()) {
				model.addAttribute("accesos", accesos); // Msj "No hay registros"
			} else {
				model.addAttribute("accesos", accesos);
			}

		} catch (Exception e) {
			System.out.println("ERROR en vistaEntradas: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error al cargar las entradas: " + e.getMessage());
		}

		return "instructor/nvasEntradas";
	}

	@GetMapping("/grupos")
	public String vistaGrupos(Model model) {
		try {
			List<Ficha> fichas = fichaRepository.findAll();

			if (fichas.isEmpty()) {
				model.addAttribute("error", "No se encontraron fichas registradas en el sistema.");
			} else {
				model.addAttribute("fichas", fichas);
				System.out.println("Fichas encontradas: " + fichas.size());
				// Debug: mostrar nombres de las fichas
				fichas.forEach(f -> System.out.println("- " + f.getNombrePrograma() + " - " + f.getNumFicha()));
			}

		} catch (Exception e) {
			model.addAttribute("error", "Error al cargar las fichas: " + e.getMessage());
			e.printStackTrace();
		}

		return "instructor/grupos";
	}

	@GetMapping("/reportes")
	public String vistaReportes(Model model) {
		return "instructor/reportes";
	}
}
