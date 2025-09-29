package com.sena.techaccess.controller;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;

	import com.sena.techaccess.model.Usuario;
	import com.sena.techaccess.service.UsuarioServiceImplement;

	@Controller
	@RequestMapping("/logIn")

	public class LoginController {

		@Autowired
		private UsuarioServiceImplement usuarioService;

		@GetMapping("/home")
		public String mostrarLogin() {
			return "usuario/home";

		}

		// process method login

		@PostMapping("/login")
		public String login(@RequestParam("correoUsuario") String email,
				@RequestParam("contrasenaUsuario") String password, Model model) {

			Usuario usuario = usuarioService.validarUsuario(email, password);

			if (usuario != null) {
				model.addAttribute("instructor", usuario);
				// Redirigir según el tipo de usuario
				switch (usuario.getRol().getId()) {
				case 3:
					return "Usuario_Interno/instructor";
				case 2:
					return "Usuario_Interno/aprendiz";
				case 1:
					return "Administrador/admin";
				default:
					return "redirect:/usuario/home?error=tipo";
				}
			} else {
				//  Usuario o contraseña incorrectos
				return "redirect:/logIn/home?error=login";
			}
		}
	}

