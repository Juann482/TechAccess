package com.sena.techaccess.controller;

import java.nio.file.Path;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.nio.file.Files;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Horario;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.repository.HorarioRepository;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	private final Logger LOGGER = LoggerFactory.getLogger(AprendizController.class);

	@Autowired
	private IExcusasService excusasService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IFichaService fichaService;

	@Autowired
	private FichaRepository fichaRepository;


	@Autowired
	private HorarioRepository horarioRepository;

	// Constructor
	public AprendizController(FichaRepository fichaRepository) {
		this.fichaRepository = fichaRepository;
	}

	// ========================= PÁGINA PRINCIPAL =========================
	@GetMapping("/aprendiz")
	public String inicioAprendiz(Model model) {
		Usuario user = usuarioService.findAll().get(0);
		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());
		model.addAttribute("estadoCuenta", user.getEstadoCuenta());
		return "Aprendiz/aprendiz";
	}

	// ========================= EXCUSAS =========================
	@GetMapping("/excusas")
	public String mostrarExcusas(Model modelo) {
		modelo.addAttribute("Excusas", new Excusas());
		return "Aprendiz/excusas";
	}

	@PostMapping("/lista")
	public String registrarExcusa(@ModelAttribute Excusas excusas, Model model) {
		Usuario user = usuarioService.findAll().get(0);
		excusasService.save(excusas);
		LOGGER.warn("Excusa registrada con éxito: {}", excusas);

		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());
		model.addAttribute("estadoCuenta", user.getEstadoCuenta());
		return "Aprendiz/excusas";
	}

	// Formulario vacío
	@GetMapping("/form")
	public String mostrarFormularioExcusa(Model model) {
		model.addAttribute("excusa", new Excusas());
		return "Aprendiz/excusaForm";
	}

	// Guardar nueva excusa con archivo
	@PostMapping("/save")
	public String guardarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
			throws IOException {
		LOGGER.info("Excusa recibida: {}", excusa);

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
			Path ruta = Paths.get("uploads").resolve(nombreArchivo);
			Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
			excusa.setSoporte(nombreArchivo);
		}

		excusasService.save(excusa);
		LOGGER.info("Excusa guardada con ID {}", excusa.getId());
		return "redirect:/Aprendiz/list";
	}

	// Listar excusas
	@GetMapping("/list")
	public String listarExcusas(Model model) {
		model.addAttribute("excusas", excusasService.findAll());
		return "Aprendiz/excusas";
	}

	// Editar
	@GetMapping("/edit/{id}")
	public String editarExcusa(@PathVariable Integer id, Model model) {
		Excusas excusa = excusasService.findById(id).orElseThrow(() -> new RuntimeException("Excusa no encontrada"));
		model.addAttribute("excusa", excusa);
		return "Aprendiz/excusaForm";
	}

	// Actualizar
	@PostMapping("/update")
	public String actualizarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
			throws IOException {

		Excusas existente = excusasService.findById(excusa.getId())
				.orElseThrow(() -> new RuntimeException("Excusa no encontrada"));

		existente.setFicha(excusa.getFicha());
		existente.setMotivo(excusa.getMotivo());
		existente.setFecha(excusa.getFecha());

		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
			Path ruta = Paths.get("uploads").resolve(nombreArchivo);
			Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
			existente.setSoporte(nombreArchivo);
		}

		excusasService.save(existente);
		LOGGER.info("Excusa actualizada ID {}", existente.getId());
		return "redirect:/Aprendiz/list";
	}

	// Eliminar
	@GetMapping("/delete/{id}")
	public String eliminarExcusa(@PathVariable Integer id) {
		excusasService.delete(id);
		LOGGER.info("Excusa eliminada ID {}", id);
		return "redirect:/Aprendiz/list";
	}

	// ========================= HORARIO =========================
	@GetMapping("/Horario")
	public String verHorario(Model model) {
		List<Horario> lista = horarioRepository.findAll();
		model.addAttribute("listaHorarios", lista);
		return "Aprendiz/Horario";
	}

	@PostMapping("/guardarHorario")
	public String guardarHorario(@RequestParam Map<String, String> params) {
		for (int i = 1; i <= 4; i++) {
			Horario h = horarioRepository.findById((long) i).orElse(new Horario());
			h.setId((long) i);

			switch (i) {
			case 1 -> h.setHora("08:00 - 10:00");
			case 2 -> h.setHora("10:00 - 12:00");
			case 3 -> h.setHora("13:00 - 15:00");
			case 4 -> h.setHora("15:00 - 17:00");
			}

			h.setLunes(params.get("lunes" + i));
			h.setMartes(params.get("martes" + i));
			h.setMiercoles(params.get("miercoles" + i));
			h.setJueves(params.get("jueves" + i));
			h.setViernes(params.get("viernes" + i));

			horarioRepository.save(h);
		}

		LOGGER.info("Horario actualizado correctamente.");
		return "redirect:/Aprendiz/Horario";
	}

}
