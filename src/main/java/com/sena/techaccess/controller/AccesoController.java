package com.sena.techaccess.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		// 1. Buscar usuario por documento / código de barras
		Usuario usuario = usuarioService.findByDocumento(documento);
		if (usuario == null) {
			return "Usuario no encontrado";
		}

		// 2. Buscar su último acceso
		Acceso ultimoAcceso = accesoService.findUltimoAcceso(usuario.getId());

		// 3. Si hay un acceso sin horaEgreso → registrar SALIDA
		if (ultimoAcceso != null && ultimoAcceso.getHoraEgreso() == null) {

			// (OPCIONAL) validar tiempo mínimo antes de salida:
			Duration dur = Duration.between(ultimoAcceso.getHoraIngreso(), LocalDateTime.now());
			if (dur.toMinutes() < 5) {
				return "Debe pasar al menos 5 minutos para registrar salida.";
			}

			ultimoAcceso.setHoraEgreso(LocalDateTime.now());
			accesoService.save(ultimoAcceso);
			return "Egreso registrado: " + usuario.getNombre();
		}

		// 4. Si no hay acceso activo → registrar NUEVA ENTRADA
		Acceso nuevoAcceso = new Acceso();
		nuevoAcceso.setUsuario(usuario);
		nuevoAcceso.setHoraIngreso(LocalDateTime.now());
		accesoService.save(nuevoAcceso);

		return "Ingreso registrado: " + usuario.getNombre();
	}
}