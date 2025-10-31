package com.sena.techaccess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.ISoporteService;
import com.sena.techaccess.model.Soporte;

@Controller
@RequestMapping("/")
public class HomeUsuarioController {
	
	/*@Autowired
	private ISoporteService soporteservice;
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("consultantes", soporteservice.findAll());
		return "/soporte";
	}

		@PostMapping("/save")
		public String create(Soporte soporte) throws IOException {
			Consultante u = consultanteservice.findById(Integer.parseInt(session.getAttribute("idUsuario¨").toString()))-get()))
		}*/

	// private final Logger LOGGER = (Logger)
	// LoggerFactory.getLogger(HomeUsuarioController.class);

	@GetMapping("")
	public String Inicio() {
		return ("redirect:/usuario/home");
	}

	
	
}
