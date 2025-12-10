package com.sena.techaccess.controller;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
public class AccesoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IAccesoService accesoService;

	@PostMapping("/registrar-ingreso")
	@ResponseBody
	public String registrarIngreso(@RequestParam("documento") String documento) {

		Usuario usuario = usuarioService.findByDocumento(documento);
		if (usuario == null) {
			LOGGER.warn("Documento no encontrado: {}", documento);
			return "Usuario no encontrado";
		}

		Acceso ultimoAcceso = accesoService.findUltimoAcceso(usuario.getId());
		LOGGER.info("Ultimo acceso encontrado para {}: {}", usuario.getNombre(), ultimoAcceso);

		if (ultimoAcceso != null && ultimoAcceso.getHoraEgreso() == null) {
			LOGGER.info("Registrando EGRESO para acceso id={}", ultimoAcceso.getId());
			ultimoAcceso.setHoraEgreso(LocalDateTime.now());
			accesoService.save(ultimoAcceso);
			return "Egreso registrado: " + usuario.getNombre();
		}

		LOGGER.info("Registrando INGRESO nuevo para usuario id={}", usuario.getId());
		Acceso nuevoAcceso = new Acceso();
		nuevoAcceso.setUsuario(usuario);
		nuevoAcceso.setHoraIngreso(LocalDateTime.now());
		accesoService.save(nuevoAcceso);

		return "Ingreso registrado: " + usuario.getNombre();
	}
}
	