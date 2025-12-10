package com.sena.techaccess.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Horario;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.HorarioRepository;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IUsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Aprendiz")
public class AprendizController {

	private final AccesoController accesoController;

	private final Logger LOGGER = LoggerFactory.getLogger(AprendizController.class);

	@Autowired
	private IExcusasService excusasService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private IAccesoService accesoService;

	AprendizController(AccesoController accesoController) {
		this.accesoController = accesoController;
	}

	// ===================== AUXILIAR: USUARIO LOGUEADO =====================

	private Usuario getUsuarioLogueado(Principal principal) {
		if (principal == null) {
			return null;
		}
		String email = principal.getName(); // el mismo que usas en ServiceLogin

		// Asegúrate de que IUsuarioService tenga este método:
		// Optional<Usuario> findByEmail(String email);
		return usuarioService.findByEmail(email).orElse(null);
	}
	// ========================= PÁGINA PRINCIPAL =========================

	@GetMapping({ "", "/inicio" })
	public String inicioAprendiz(Model model, Principal principal) {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());
		model.addAttribute("estadoCuenta", user.getEstadoCuenta());

		// Obtener último acceso real
		Acceso ultimoAcceso = accesoService.findUltimoAcceso(user.getId());
		model.addAttribute("ultimoAcceso", ultimoAcceso);

		// Cálculo seguro del tiempo dentro
		String tiempoDentro;

		if (ultimoAcceso != null && ultimoAcceso.getHoraIngreso() != null && ultimoAcceso.getHoraEgreso() == null) {

			Duration dur = Duration.between(ultimoAcceso.getHoraIngreso(), LocalDateTime.now());

			long horas = dur.toHours();
			long minutos = dur.toMinutesPart();

			tiempoDentro = horas + "h " + minutos + "m";

		} else {
			tiempoDentro = "Fuera del centro";
		}

		model.addAttribute("tiempoDentro", tiempoDentro);

		return "Aprendiz/aprendiz";
	}

	// =============historial ingresos egresos ==============

	@GetMapping("/historial")
	@ResponseBody
	public List<Map<String, String>> obtenerHistorial(Principal principal) {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return List.of();
		}

		List<Acceso> historial = accesoService.findByUsuarioIdOrderByHoraIngresoDesc(user.getId());

		return historial.stream()
				.map(a -> Map.of("ingreso", a.getHoraIngreso() != null ? a.getHoraIngreso().toString() : "Sin registro",
						"egreso", a.getHoraEgreso() != null ? a.getHoraEgreso().toString() : "Sin registro"))
				.toList();
	}

	// ========================= PERFIL =========================

	@GetMapping("/perfil")
	public String perfil(Model model, Principal principal) {
		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", user);
		model.addAttribute("documento", user.getDocumento());
		return "Aprendiz/perfil";
	}

	// ========================= EXCUSAS =========================

	@GetMapping("/excusas")
	public String inicioExcusas(Model model, Principal principal) {
		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", user);
		model.addAttribute("ficha", user.getFicha());

		// AHORA: solo excusas del usuario logueado
		List<Excusas> excusas = excusasService.findByUsuario(user);
		model.addAttribute("excusas", excusas);

		return "Aprendiz/excusas";

	}

	@GetMapping("/list")
	public String listarExcusas(Principal principal) {
		// toda la lógica está en /excusas
		return "redirect:/Aprendiz/excusas";
	}

	@GetMapping("/form")
	public String mostrarFormularioExcusa(Model model, Principal principal) {
		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("excusa", new Excusas());
		return  "redirect:/Aprendiz/excusas";
	}

	@PostMapping("/save")
	public String guardarExcusa(@Valid @ModelAttribute("excusa") Excusas excusa, BindingResult result,
			@RequestParam("img") MultipartFile archivo, RedirectAttributes redirectAttributes, Principal principal) {

		try {
			Usuario user = getUsuarioLogueado(principal);
			if (user == null) {
				redirectAttributes.addFlashAttribute("error", "No se pudo identificar al usuario logueado.");
				return "redirect:/login";
			}

			excusa.setUsuario(user);
			excusa.setFicha(user.getFicha());

			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("error", "Error de validación en el formulario.");
				return "redirect:/Aprendiz/form";
			}

			Excusas existente = (excusa.getId() != null) ? excusasService.findById(excusa.getId()).orElse(null) : null;

			if (archivo.isEmpty() && (existente == null || existente.getSoporte() == null)) {
				redirectAttributes.addFlashAttribute("error", "Debes adjuntar un soporte (PDF o imagen).");
				return "redirect:/Aprendiz/form";
			}

			if (!archivo.isEmpty()) {
				String tipo = archivo.getContentType();
				if (tipo == null || !(tipo.startsWith("image/") || tipo.equals("application/pdf"))) {
					redirectAttributes.addFlashAttribute("error", "El archivo debe ser una imagen o un PDF.");
					return "redirect:/Aprendiz/form";
				}

				excusa.setSoporte(guardarArchivoSoporte(archivo));
			} else if (existente != null) {
				excusa.setSoporte(existente.getSoporte());
			}

			excusasService.save(excusa);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Excusa guardada correctamente.");
			return "redirect:/Aprendiz/excusas";

		} catch (Exception e) {
			LOGGER.error("Error al guardar la excusa", e);
			redirectAttributes.addFlashAttribute("error", "Error al guardar la excusa: " + e.getMessage());
			return "redirect:/Aprendiz/excusas";
		}
	}

	@GetMapping("/excusa/{id}")
	public String verExcusa(@PathVariable Integer id, Model model, Principal principal,
			RedirectAttributes redirectAttributes) {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		Excusas excusa = excusasService.findById(id).orElse(null);
		if (excusa == null) {
			redirectAttributes.addFlashAttribute("error", "Excusa no encontrada.");
			return "redirect:/Aprendiz/excusas";
		}

		if (!excusa.getUsuario().getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("error", "Acceso denegado: Esta excusa no te pertenece.");
			return "redirect:/Aprendiz/excusas";
		}

		model.addAttribute("excusa", excusa);
		return "Aprendiz/detalle_excusa";
	}

	@GetMapping("/edit/{id}")
	public String editarExcusa(@PathVariable Integer id, Model model, Principal principal,
			RedirectAttributes redirectAttributes) {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		Excusas excusa = excusasService.findById(id).orElse(null);
		if (excusa == null) {
			redirectAttributes.addFlashAttribute("error", "Excusa no encontrada para edición.");
			return "redirect:/Aprendiz/excusas";
		}

		if (!excusa.getUsuario().getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("error", "Acceso denegado: No puedes editar esta excusa.");
			return "redirect:/Aprendiz/excusas";
		}

		model.addAttribute("excusa", excusa);
		return "Aprendiz/excusaForm";
	}

	@PostMapping("/update")
	public String actualizarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo,
			Principal principal, RedirectAttributes redirectAttributes) throws IOException {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		Excusas existente = excusasService.findById(excusa.getId()).orElse(null);
		if (existente == null) {
			redirectAttributes.addFlashAttribute("error", "Excusa a actualizar no encontrada.");
			return "redirect:/Aprendiz/excusas";
		}

		if (!existente.getUsuario().getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("error", "Acceso denegado: No puedes modificar una excusa ajena.");
			return "redirect:/Aprendiz/excusas";
		}

		existente.setMotivo(excusa.getMotivo());
		existente.setFecha(excusa.getFecha());

		if (!archivo.isEmpty()) {
			existente.setSoporte(guardarArchivoSoporte(archivo));
		}

		excusasService.save(existente);
		redirectAttributes.addFlashAttribute("mensaje", "✅ Excusa actualizada correctamente.");
		return "redirect:/Aprendiz/excusas";
	}

	@GetMapping("/delete/{id}")
	public String eliminarExcusa(@PathVariable Integer id, Principal principal, RedirectAttributes redirectAttributes) {

		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		Excusas excusa = excusasService.findById(id).orElse(null);
		if (excusa == null) {
			redirectAttributes.addFlashAttribute("error", "Excusa a eliminar no encontrada.");
			return "redirect:/Aprendiz/excusas";
		}

		if (!excusa.getUsuario().getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("error", "Acceso denegado: No puedes eliminar una excusa ajena.");
			return "redirect:/Aprendiz/excusas";
		}

		excusasService.delete(id);
		redirectAttributes.addFlashAttribute("mensaje", "✅ Excusa eliminada correctamente.");
		return "redirect:/Aprendiz/excusas";
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

	@GetMapping("/detalleExcusa")
	public String detalleExcusa(Model model, Principal principal) {
		Usuario user = getUsuarioLogueado(principal);
		if (user == null) {
			return "redirect:/login";
		}

		// También solo las excusas del usuario logueado
		List<Excusas> excusas = excusasService.findByUsuario(user);
		model.addAttribute("excusas", excusas);

		return "Aprendiz/detalleExcusa";
	}

	// ================== AUXILIAR: GUARDAR ARCHIVO ==================

	private String guardarArchivoSoporte(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString().substring(0, 8) + "_"
				+ StringUtils.cleanPath(archivo.getOriginalFilename());
		Path dir = Paths.get("uploads");
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}
		Path ruta = dir.resolve(nombreArchivo);
		Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
		return nombreArchivo;
	}
}