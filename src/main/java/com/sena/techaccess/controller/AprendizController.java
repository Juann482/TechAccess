package com.sena.techaccess.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.service.IEstadoCuentaService;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	// >private final Logger LOGGER = LoggerFactory.getLogger(AprendizController.class); 

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

	@GetMapping("/Aprendiz/datosUser")
	public String mostrar(Model model) {
		@SuppressWarnings("unused")
		Excusas excusas = new Excusas(); // o cargar desde DB
		model.addAttribute("usuario", usuarioService.findAll());
		model.addAttribute("Excusas", excusasService.findAll());
		model.addAttribute("Excusas", new Excusas());
		model.addAttribute("nombre", usuarioService.findAll());
		model.addAttribute("numFicha", fichaService.findAll());
		model.addAttribute("nombreEstado", estadocuentaService.findAll());

		return "Aprendiz/aprendiz";
	}

}