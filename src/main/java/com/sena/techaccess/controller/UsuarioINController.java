package com.sena.techaccess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Rol;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.EstadoCuentaRepository;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.repository.RolRepository;
import com.sena.techaccess.repository.UsuarioRepository;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/usuario_Interno")
public class UsuarioINController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(UsuarioINController.class);
	

    private final UsuarioRepository usuarioRepository;
    private final FichaRepository fichaRepository;
    private final EstadoCuentaRepository estadoCuentaRepository;
    private final IUsuarioService usuarioService;
    private final RolRepository rolRepository;

    @Autowired
    public UsuarioINController(UsuarioRepository usuarioRepository,
                               FichaRepository fichaRepository,
                               EstadoCuentaRepository estadoCuentaRepository,
                               IUsuarioService usuarioService,
                               RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.fichaRepository = fichaRepository;
        this.estadoCuentaRepository = estadoCuentaRepository;
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
    }
	
	@GetMapping("/funcionarioF/funcionarioDashboard")
	public String inicioFuncionario() {
		return "usuario_Interno/funcionarioF/funcionarioDashboard";

	}

	//==================================== FOrmulario -- Registro=====================================
	@GetMapping("/funcionarioF/form")
	public String mostrarFormulario(Model model) {

		model.addAttribute("usuarios", usuarioRepository.findAll());
		model.addAttribute("usuario", new Usuario()); // objeto vacio
		model.addAttribute("roles", rolRepository.findAll()); // Lista de roles para cargar
		model.addAttribute("estados", estadoCuentaRepository.findAll()); // Lista de estados para cargar

		return "usuario_Interno/funcionarioF/funcionarioRegistro";
	}

	@PostMapping("/funcionarioF/form")		
	public String guardarRegistro(Usuario usuario) {
		LOGGER.warn("UsuarioFormulario {}", usuario);
		Rol rol = rolRepository.findById(usuario.getRol().getId())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
		usuario.setRol(rol);
		usuarioRepository.save(usuario);
		LOGGER.warn("Usuario guardado: {}", usuario.getNombre());
		return "redirect:/usuario_Interno/funcionarioF/funcionarioDashboard";
	}
	
	//===========================Listado de usuarios===========================
	
	@GetMapping("/funcionarioF/listado")
	public String enlistarUsuarios(Model model) {
		
		model.addAttribute("usuarios", usuarioService.findAll());//Listado de usuarios
		//.orElseThrow(() -> new RuntimeException("No encontrado")) ;
		return "usuario_Interno/funcionarioF/funcionarioDashboard";
		
		
	}
	
	//======================================Ficha - enlistado y registro====================================0
	
	@GetMapping("/funcionarioF/fichas")
	public String asignarCampoFichas(Model model) {
		
		model.addAttribute("fichas", fichaRepository.findAll());
		model.addAttribute("ficha", new Ficha());
		
		return "usuario_Interno/funcionarioF/funcionarioFichas";
	}
	
	@PostMapping("/funcionarioF/fichaSave")
	public String guardarFicha(Ficha ficha) {
		
		fichaRepository.save(ficha);
		LOGGER.debug("La ficha se ha registrado con exito {}", ficha);
		
		return "redirect:/funcionarioF/fichas";
	}
	

}
