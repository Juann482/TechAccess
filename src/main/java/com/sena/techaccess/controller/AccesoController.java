package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.techaccess.service.IAccesoService;

public class AccesoController {

	@Autowired
	private IAccesoService accesoService;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("accesos", accesoService.findAll());
		return "usuario/home";
	}	
	//Modulo de controlmde accesos a el panel entradas(Instructor)
	
	/*
	@GetMapping("/entradas/editar/{id}")
	public String editarEntrada(@PathVariable Long id, Model model) {
	    Entrada entrada = entradaService.obtenerPorId(id);
	    model.addAttribute("entrada", entrada);
	    return "acceso/form-editar"; // vista del formulario de edición
	}

	@GetMapping("/entradas/eliminar/{id}")
	public String eliminarEntrada(@PathVariable Long id) {
	    entradaService.eliminar(id);
	    return "redirect:/acceso/listar";
	}
*/

}