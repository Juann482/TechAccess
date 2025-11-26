package com.sena.techaccess.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Administrador")
public class AdministradorController {

    private final PasswordEncoder passwordEncoder;

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(AdministradorController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;
	
	@Autowired
	private HttpSession session;
	
	

	BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

    AdministradorController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping
    private String datosUser(Usuario usuario, HttpSession session) {
    	
    	String nombre = (String)session.getAttribute("nombre");
    	return "";
    }
    

	// ================================ REGISTRO ============================

	@GetMapping("/form")
	public String mostrarFormulario(Model model) {

		model.addAttribute("usuarios", usuarioService.findAll());
		model.addAttribute("usuario", new Usuario()); // objeto vacio
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas

		return "Administrador/RegistroUSER";
	}

	@PostMapping("/form")
	public String guardarRegistro(Usuario usuario) {

		usuario.setPassword(pe.encode(usuario.getPassword()));
		usuario.setEstadoCuenta("Activo");
		if (usuario.getFicha() != null && usuario.getFicha().getIdFicha() == null) {
		    usuario.setFicha(null);
		}
		usuarioService.save(usuario);
		LOGGER.warn("Usuario guardado: {}", usuario.getNombre());
		return "redirect:/Administrador/usuarios";
	}

	@GetMapping("/cambiarEstado/{id}")
	public String cambiarEstado(@PathVariable Integer id) {

		Usuario u = usuarioService.get(id).orElse(null);		
		if (u != null) {
			if ("Activo".equalsIgnoreCase(u.getEstadoCuenta())) {
				u.setEstadoCuenta("Inactivo");
			} else {
				u.setEstadoCuenta("Activo");
			}
		}

		usuarioService.save(u);

		return "redirect:/Administrador/usuarios"; // Cambia esto a tu ruta real
	}

	// ========================== DASHBOAD =========================

	// Enlistado de usuarios
	@GetMapping("/usuarios")
	public String enlistarUsuarios(
	        @RequestParam(required = false) String nombre,
	        @RequestParam(required = false) String documento,
	        @RequestParam(required = false) String rol,
	        @RequestParam(required = false) String estado,
	        Model model) {

	    model.addAttribute("Usuarios",
	            usuarioService.filtrarUsuarios(nombre, documento, rol, estado));

	    model.addAttribute("nombre", nombre);
	    model.addAttribute("documento", documento);
	    model.addAttribute("rol", rol);
	    model.addAttribute("estado", estado);

	    return "Administrador/HistorialUSER";
	}


	// Editar usuario
	@GetMapping("/edicionUsuario/{id}")
	public String edicionUsuario(@PathVariable Integer id, Model model) {
		Usuario userEd = new Usuario(); // Objeto vacio
		Optional<Usuario> ud = usuarioService.get(id); // Llamas a tu servicio para buscar el usuario por su id.
		userEd = ud.get();
		LOGGER.warn("Busqueda de usuarios por id {}", userEd);
		model.addAttribute("fichas", fichaService.findAll()); // Lista de fichas
		model.addAttribute("usuario", userEd);
		return "Administrador/EdicionUSER";

	}

	// Actualizar usuario
	@PostMapping("/updateUsuario")
	public String actualizarUsuario(Usuario usuarioForm) {
	    LOGGER.info("Objeto recibido para actualizar: {}", usuarioForm);

	    // Usuario de la BD
	    Usuario usuarioDB = usuarioService.get(usuarioForm.getId())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    // --- LÓGICA DE CONTRASEÑA ---
	    if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isBlank()) {
	        usuarioDB.setPassword(pe.encode(usuarioForm.getPassword()));
	    }
	    // Si viene vacía, NO tocamos la contraseña → se mantiene la actual

	    // Actualizamos los otros campos
	    usuarioDB.setNombre(usuarioForm.getNombre());
	    usuarioDB.setDireccion(usuarioForm.getDireccion());
	    usuarioDB.setDocumento(usuarioForm.getDocumento());
	    usuarioDB.setTelefono(usuarioForm.getTelefono());
	    usuarioDB.setEmail(usuarioForm.getEmail());
	    usuarioDB.setRol(usuarioForm.getRol());

	    usuarioService.update(usuarioDB);

	    LOGGER.warn("Usuario actualizado: {}", usuarioDB);
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

	// enlistar ficha
	@GetMapping("/Historialfichas")
	public String asignarCampoFichas(Model model) {

		model.addAttribute("fichas", fichaService.findAll());
		model.addAttribute("ficha", new Ficha());

		return "Administrador/HistorialFICHAS";
	}

	@GetMapping("/GuardarFicha")
	public String GuardarFicha(Ficha ficha) {
		return "Administrador/RegistroFICHA";
	}

	// Guardar ficha
	@PostMapping("/fichaSave")
	public String guardarFicha(Ficha ficha) {

		fichaService.save(ficha);
		LOGGER.debug("La ficha se ha registrado con exito {}", ficha);

		return "redirect:/Administrador/Historialfichas";
	}

	// Editar ficha
	@GetMapping("/EdicionFichas/{idFicha}")
	public String edicionFicha(@PathVariable Integer idFicha, Model model) {

		Ficha ut = fichaService.get(idFicha).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		LOGGER.warn("Busqueda de fichas por id {}", ut);
		model.addAttribute("ficha", ut);
		return "Administrador/EdicionFICHA";

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
		return "redirect:/Administrador/Historialfichas";
	}

	// Eliminar ficha
	@GetMapping("/delete/{idFicha}")
	public String eliminarFicha(@PathVariable Integer idFicha) {
		Ficha fichaEl = new Ficha();
		fichaEl = fichaService.get(idFicha).get();
		fichaService.delete(idFicha);
		LOGGER.warn("Ficha eliminada: {}", fichaEl);
		return "redirect:/Administrador/Historialfichas";

	}
	
	@GetMapping("/Perfil")
	private String Perfil () {
		return "Administrador/VerPerfil";
	}

}
