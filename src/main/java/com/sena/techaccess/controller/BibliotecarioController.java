package com.sena.techaccess.controller;

import com.sena.techaccess.model.Bibliotecario;
import com.sena.techaccess.service.IBibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class BibliotecarioController {

	@Autowired
	private IBibliotecarioService libroService;

	@GetMapping("/Bibliotecario")
	public String mostrarPanelFuncionario() {
		return "Funcionarios/Bibliotecario";
	}

	@GetMapping
	public String listarLibros(Model model) {
		model.addAttribute("titulo", "Gestión de Libros");
		model.addAttribute("libros", libroService.listarTodos());
		return "libros/lista";
	}

	@GetMapping("/nuevo")
	public String mostrarFormularioNuevoLibro(Model model) {
		model.addAttribute("libro", new Bibliotecario());
		model.addAttribute("titulo", "Nuevo Libro");
		return "libros/formulario";
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditarLibro(@PathVariable Integer id, Model model, RedirectAttributes flash) {
		Bibliotecario libro = null;

		if (id > 0) {
			libro = libroService.findById(id);
			if (libro == null) {
				flash.addFlashAttribute("error", "El ID del libro no existe en la base de datos.");
				return "redirect:/libros";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del libro no puede ser cero.");
			return "redirect:/libros";
		}

		model.addAttribute("titulo", "Editar Libro");
		model.addAttribute("libro", libro);

		return "libros/formulario";
	}
}