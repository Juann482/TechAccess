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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Horario;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.repository.HorarioRepository;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IUsuarioService;

import jakarta.validation.Valid;

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

	// ===================== Codigo Barras ======================

	@GetMapping("/perfil")
	public String perfil(Model model) {
		String documento = "1234567890"; 
		model.addAttribute("documento", documento);
		return "perfil";
	}

	// ========================= EXCUSAS =========================

	@GetMapping("/excusas")
	public String inicioexcusas(Model model) {
		Usuario user = usuarioService.findAll().get(0);
		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());
		return "Aprendiz/excusas";
	}

	@PostMapping("/lista")
	public String registrarExcusa(@ModelAttribute Excusas excusas, Model model) {
		Usuario user = usuarioService.findAll().get(0);
		excusasService.save(excusas);
		LOGGER.warn("Excusa registrada con éxito: {}", excusas);

		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());
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
	public String guardarExcusa(@Valid @ModelAttribute("excusa") Excusas excusa, BindingResult result,
			@RequestParam("img") MultipartFile archivo, RedirectAttributes redirectAttributes, Model model) {

		try {
			Usuario user = usuarioService.findAll().get(0); // usuario logueado o simulado
			excusa.setUsuario(user); // ✅ asegura que tenga usuario asignado
			excusa.setFicha(user.getFicha()); // ✅ también asigna ficha

			// 1️⃣ Validar campos del modelo
			if (result.hasErrors()) {
				StringBuilder errores = new StringBuilder("Por favor completa todos los campos obligatorios: ");
				result.getFieldErrors().forEach(err -> errores.append(err.getDefaultMessage()).append(". "));
				redirectAttributes.addFlashAttribute("error", errores.toString());
				return "redirect:/Aprendiz/excusas";
			}

			// 2️⃣ Validar archivo obligatorio
			Excusas existente = (excusa.getId() != null) ? excusasService.findById(excusa.getId()).orElse(null) : null;

			if (archivo.isEmpty() && (existente == null || existente.getSoporte() == null)) {
				redirectAttributes.addFlashAttribute("error", "Debes adjuntar un soporte (PDF o imagen).");
				return "redirect:/Aprendiz/excusas";
			}

			// 3️⃣ Validar tipo de archivo
			if (!archivo.isEmpty()) {
				String tipo = archivo.getContentType();
				if (tipo == null || !(tipo.startsWith("image/") || tipo.equals("application/pdf"))) {
					redirectAttributes.addFlashAttribute("error", "El archivo debe ser una imagen o un PDF.");
					return "redirect:/Aprendiz/excusas";
				}

				// Guardar archivo
				String nombreArchivo = UUID.randomUUID().toString().substring(0, 8) + "_"
						+ StringUtils.cleanPath(archivo.getOriginalFilename());
				Path dir = Paths.get("uploads");
				if (!Files.exists(dir))
					Files.createDirectories(dir);
				Path ruta = dir.resolve(nombreArchivo);
				Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
				excusa.setSoporte(nombreArchivo);
			} else if (existente != null) {
				excusa.setSoporte(existente.getSoporte());
			}

			// 4️⃣ Guardar en BD
			excusasService.save(excusa);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Excusa guardada correctamente.");
			return "redirect:/Aprendiz/excusas";

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al guardar la excusa: " + e.getMessage());
			return "redirect:/Aprendiz/excusas";
		}
	}

	// detale excusa
	@GetMapping("/Aprendiz/excusa/{id}")
	public String verExcusa(@PathVariable Integer id, Model model) {
		Excusas excusa = excusasService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Excusa no encontrada con id: " + id));

		model.addAttribute("excusa", excusa);
		return "Aprendiz/detalle_excusa"; // Nombre del template HTML
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
		existente.setUsuario(excusa.getUsuario());

		if (!archivo.isEmpty()) {
			String nombreArchivo = archivo.getOriginalFilename();
			Path ruta = Paths.get("uploads/" + nombreArchivo);
			Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
			excusa.setSoporte(nombreArchivo);
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

			horarioRepository.save(h);
		}

		LOGGER.info("Horario actualizado correctamente.");
		return "redirect:/Aprendiz/Horario";
	}

	///////////// Detalle Excusa/////////////////////

	@GetMapping("/detalleExcusa")
	public String detalleExcusa(Model model) {
		List<Excusas> listaExcusas = excusasService.findAll();
		model.addAttribute("excusas", listaExcusas);
		return "Aprendiz/detalleExcusa";
	}

}
