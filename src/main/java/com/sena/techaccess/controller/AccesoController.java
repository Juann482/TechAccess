package com.sena.techaccess.controller;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.repository.AccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccesoController {

	@Autowired
	private AccesoRepository accesoRepository;

	@GetMapping("/instructor/grupos")
	public String mostrarGrupos(Model model) {
		List<Acceso> accesos = accesoRepository.findAll();
		model.addAttribute("accesos", accesos);
		return "instructor";
	}

	// Modulo de controlmde accesos a el panel entradas(Instructor)

	/*
	 * @GetMapping("/entradas/editar/{id}") public String
	 * editarEntrada(@PathVariable Long id, Model model) { Entrada entrada =
	 * entradaService.obtenerPorId(id); model.addAttribute("entrada", entrada);
	 * return "acceso/form-editar"; // vista del formulario de edición }
	 * 
	 * @GetMapping("/entradas/eliminar/{id}") public String
	 * eliminarEntrada(@PathVariable Long id) { entradaService.eliminar(id); return
	 * "redirect:/acceso/listar"; }
	 */

}