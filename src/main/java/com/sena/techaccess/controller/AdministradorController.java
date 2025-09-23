package com.sena.techaccess.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Rol;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IEstadoCuentaService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IRolService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Administrador")
public class AdministradorController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IRolService rolService;

	@Autowired
	private IEstadoCuentaService estadoCuentaService;

	@Autowired
	private IFichaService fichaService;

	@GetMapping("/Dashboard")
	public String inicioFuncionario() {
		return "Administrador/Dashboard";
	}

	// ================================ REGISTRO ============================

	@GetMapping("/form")
	public String mostrarFormulario(Model model) {

		model.addAttribute("usuarios", usuarioService.findAll());
		model.addAttribute("usuario", new Usuario()); // objeto vacio
		model.addAttribute("roles", rolService.findAll()); // Lista de roles para cargar
		model.addAttribute("estados", estadoCuentaService.findAll()); // Lista de estados para cargar
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas

		return "Administrador/Registro";
	}

	@PostMapping("/form")
	public String guardarRegistro(Usuario usuario) {
		LOGGER.warn("UsuarioFormulario {}", usuario);
		Rol rol = rolService.findById(usuario.getRol().getId())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
		usuario.setRol(rol);
		usuarioService.save(usuario);
		LOGGER.warn("Usuario guardado: {}", usuario.getNombre());
		return "redirect:/Administrador/usuarios";
	}

	// ========================== DASHBOAD =========================

	// Enlistado de usuarios
	@GetMapping("/usuarios") /// funcionarioF/Dashboard
	public String enlistarUsuarios(Model model) {
		

		model.addAttribute("Usuarios", usuarioService.findAll()); // Listado de usuarios
		model.addAttribute("usuario", new Usuario());
		return "Administrador/Dashboard";

	}

	// Editar usuario
	@GetMapping("/edicionUsuario/{id}")
	public String edicionUsuario(@PathVariable Integer id, Model model) {
		Usuario userEd = new Usuario();
		Optional<Usuario> ud = usuarioService.get(id);
		userEd = ud.get();
		LOGGER.warn("Busqueda de usuarios por id {}", userEd);
		model.addAttribute("roles", rolService.findAll()); // Lista de roles para cargar
		model.addAttribute("estados", estadoCuentaService.findAll()); // Lista de estados para cargar
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas
		model.addAttribute("usuario", userEd);
		return "Administrador/edicionUsuarios";

	}

	// Actualizar usuario
	@PostMapping("/updateUsuario")
	public String actualizarUsuario(Usuario usuario) {
	    LOGGER.info("Objeto recibido para actualizar: {}", usuario);

	    // Obtener usuario existente de la DB
	    Usuario usuarioExistente = usuarioService.get(usuario.getId())
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    // Nombre
	    if (usuario.getNombre() != null && !usuario.getNombre().equals(usuarioExistente.getNombre())) {
	        usuarioExistente.setNombre(usuario.getNombre());
	    }

	    // Documento
	    if (usuario.getDocumento() != null && !usuario.getDocumento().equals(usuarioExistente.getDocumento())) {
	        usuarioExistente.setDocumento(usuario.getDocumento());
	    }

	    // Rol
	    if (usuario.getRol() != null && !usuario.getRol().getId().equals(usuarioExistente.getRol().getId())) {
	        Rol rol = rolService.findById(usuario.getRol().getId())
	            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
	        usuarioExistente.setRol(rol);
	    }

	    // Estado
	    if (usuario.getEstadoCuenta() != null && !usuario.getEstadoCuenta().getIdEstado()
	            .equals(usuarioExistente.getEstadoCuenta().getIdEstado())) {
	        usuarioExistente.setEstadoCuenta(
	            estadoCuentaService.findById(usuario.getEstadoCuenta().getIdEstado())
	            .orElseThrow(() -> new RuntimeException("Estado no encontrado"))
	        );
	    }

	    // Ficha (opcional)
	    if (usuario.getFicha() != null && usuario.getFicha().getIdFicha() != null) {
	        usuarioExistente.setFicha(
	            fichaService.get(usuario.getFicha().getIdFicha())
	            .orElseThrow(() -> new RuntimeException("Ficha no encontrada"))
	        );
	    } else {
	        usuarioExistente.setFicha(null); // si no selecciona ficha, queda vacía
	    }

	    // Guardar cambios
	    usuarioService.update(usuarioExistente);
	    LOGGER.warn("Usuario actualizado: {}", usuarioExistente);

	    return "redirect:/Administrador/usuarios";
	}


	// Eliminacion de usuarios
	@GetMapping("/deleteUser/{id}")
	public String eliminarUsuario(@PathVariable Integer id) {
		Usuario user = new Usuario();
		user = usuarioService.get(id).get();
		usuarioService.delete(id);
		LOGGER.warn("Usuario eliminado {}", user);
		return "redirect:/Administrador/usuarios";

	}

	// ======================= FICHA ===========================

	// crear ficha
	@GetMapping("/fichas")
	public String asignarCampoFichas(Model model) {

		model.addAttribute("fichas", fichaService.findAll());
		model.addAttribute("ficha", new Ficha());

		return "Administrador/fichas";
	}

	// Guardar ficha
	@PostMapping("/fichaSave")
	public String guardarFicha(Ficha ficha) {

		fichaService.save(ficha);
		LOGGER.debug("La ficha se ha registrado con exito {}", ficha);

		return "redirect:/Administrador/fichas";
	}

	// Editar ficha
	@GetMapping("/EdicionFichas/{idFicha}")
	public String edicionFicha(@PathVariable Integer idFicha, Model model) {
		Ficha fichaEd = new Ficha();
		Optional<Ficha> ut = fichaService.get(idFicha);
		fichaEd = ut.get();
		LOGGER.warn("Busqueda de fichas por id {}", fichaEd);
		model.addAttribute("ficha", fichaEd);
		return "Administrador/EdicionFichas";

	}

	// Actualizar ficha
	@PostMapping("/update")
	public String actualizarFicha(Ficha ficha) {
		LOGGER.info("Este es el objeto del producto a actualizar en la DB {}", ficha);
		Ficha fichaAc = new Ficha();
		fichaAc = fichaService.get(ficha.getIdFicha()).get();
		ficha.setUsuario(fichaAc.getUsuario());
		fichaService.update(ficha);
		LOGGER.warn("Ficha actualizada: {}", fichaAc);
		return "redirect:/Administrador/fichas";
	}

	// Eliminar ficha
	@GetMapping("/delete/{idFicha}")
	public String eliminarFicha(@PathVariable Integer idFicha) {
		Ficha fichaEl = new Ficha();
		fichaEl = fichaService.get(idFicha).get();
		fichaService.delete(idFicha);
		LOGGER.warn("Ficha eliminada: {}", fichaEl);
		return "redirect:/Administrador/fichas";

	}

}
