package com.sena.techaccess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/usuario_Interno")
public class UsuarioINController {

	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioINController.class);

	@Autowired
	private IUsuarioService usuarioService;

	/**
	 * Mostrar formulario de aprendiz con un objeto vacío para evitar null
	 */
	@GetMapping("/Aprendiz/aprendiz")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuario", new Usuario()); // evita null en thymeleaf
		return "usuario_Interno/Aprendiz/aprendiz";
	}

	/**
	 * Guardar usuario desde el formulario y mostrarlo en el perfil
	 */
	@PostMapping("/Aprendiz/aprendiz")
	public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
		usuarioService.save(usuario); // guarda en BD
		LOGGER.info("Usuario guardado: {}", usuario);

		// enviamos el usuario guardado a la vista
		model.addAttribute("usuario", usuario);
		return "usuario_Interno/Aprendiz/aprendiz";
	}

	@GetMapping("/Aprendiz/excusas")
	public String excusas() {
		return "usuario_Interno/Aprendiz/excusas";
	}

}
