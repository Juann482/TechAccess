package com.sena.techaccess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Permisos;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IPermisosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

	@Autowired
	private IAccesoService accesoService;

	@Autowired
	private IFichaService fichaService;

	@Autowired
	private IPermisosService permisosService;

	@GetMapping("/Inicio")
	public String vistaPrincipal(Model model) {
		model.addAttribute("acceso", accesoService.findAll());
		return "instructor/instructor";
	}

	@GetMapping("/nvasEntradas")
	public String vistaEntradas(Model model) {
		try {
			List<Acceso> accesos = accesoService.findAll();

			System.out.println("=== DEBUG NUEVAS ENTRADAS ===");
			System.out.println("Número de accesos encontrados: " + accesos.size());

			// mostrar info del primer acceso de existir
			if (!accesos.isEmpty()) {
				Acceso primerAcceso = accesos.get(0);
				System.out.println("Primer acceso - ID: " + primerAcceso.getIdacceso());
				System.out.println("Usuario: "
						+ (primerAcceso.getUsuario() != null ? primerAcceso.getUsuario().getNombre() : "NULL"));
			}

			if (accesos.isEmpty()) {
				model.addAttribute("accesos", accesos); // Msj "No hay registros"
			} else {
				model.addAttribute("accesos", accesos);
			}

		} catch (Exception e) {
			System.out.println("ERROR en vistaEntradas: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "Error al cargar las entradas: " + e.getMessage());
		}

		return "instructor/nvasEntradas";
	}

	@GetMapping("/grupos")
	public String vistaGrupos(Model model) {
		try {
			List<Ficha> fichas = fichaService.findAll();

			if (fichas.isEmpty()) {
				model.addAttribute("error", "No se encontraron fichas registradas en el sistema.");
			} else {
				model.addAttribute("fichas", fichas);
				System.out.println("Fichas encontradas: " + fichas.size());
				// Debug: mostrar nombres de las fichas
				fichas.forEach(f -> System.out.println("- " + f.getNombrePrograma() + " - " + f.getNumFicha()));
			}

		} catch (Exception e) {
			model.addAttribute("error", "Error al cargar las fichas: " + e.getMessage());
			e.printStackTrace();
		}

		return "instructor/grupos";
	}

	@GetMapping("/reportes")
	public String vistaReportes(@RequestParam(defaultValue = "0") int page, Model model) {

		// Restringe 50 en grupos de 5 páginas-->
		int tamañoPagina = 10;
		int maxPaginas = 5;

		List<Permisos> todos = permisosService.findAll();

		// Ordena del más nuevo al más viejo
		Collections.reverse(todos);

		// Limita a máximo 50 registros
		if (todos.size() > 50) {
			todos = todos.subList(0, 50);
		}

		// Metodo der rango de muestreo de registro
		int inicio = page * tamañoPagina;
		int fin = Math.min(inicio + tamañoPagina, todos.size());

		List<Permisos> permisosPagina = todos.subList(inicio, fin);

		// Calcular páginas
		int totalPaginas = (int) Math.ceil((double) todos.size() / tamañoPagina);

		model.addAttribute("permisos", permisosPagina);
		model.addAttribute("totalPermisos", todos.size());
		model.addAttribute("paginaActual", page);
		model.addAttribute("totalPaginas", totalPaginas);

		return "instructor/reportes";
	}
}
