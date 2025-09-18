package com.sena.techaccess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.service.IEstadoCuentaService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IRolService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/usuario_Interno")
public class UsuarioINController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UsuarioINController.class);

	@Autowired
	private IRolService rolService;

	@Autowired
	private IEstadoCuentaService estadoCuentaService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;

	

	

	@GetMapping("/Aprendiz/aprendiz")
	public String aprendiz() {
		return "usuario_Interno/Aprendiz/aprendiz";
	}

	@GetMapping("/Aprendiz/excusas")
	public String excusas() {
		return "usuario_Interno/Aprendiz/excusas";
	}


	

	

}
