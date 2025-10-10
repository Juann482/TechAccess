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
	    List<Acceso> accesos = accesoRepository.findAll();
	    System.out.println("Número de accesos encontrados: " + accesos.size()); // Para debug
	    model.addAttribute("accesos", accesos);
	    return "instructor/nvasEntradas";
	}

	@GetMapping("/grupos")
	public String vistaGrupos(Model model) {
		String nombrePrograma = "ADSo";
		Ficha ficha = fichaRepository.findByNombrePrograma(nombrePrograma);

		if (ficha == null) {
			model.addAttribute("error", "No se encontró ninguna ficha con el nombre del programa especificado.");
			return "/instructor/error";
		}

		model.addAttribute("ficha", ficha);
		return "/instructor/grupos";
	}

	@GetMapping("/reportes")
	public String vistaReportes(Model model) {
		return "instructor/reportes";
	}
}
