package com.sena.techaccess.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.repository.FichaRepository;
import com.sena.techaccess.service.IEstadoCuentaService;
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
    private IEstadoCuentaService estadoCuentaService;

    // =========================== MÉTODOS DE NAVEGACIÓN ===========================
    @GetMapping("/aprendiz")
    public String inicioAprendiz(Model model) {
        Usuario user = usuarioService.findAll().get(0);
        model.addAttribute("usuario", user);
        model.addAttribute("ficha", user.getFicha());
        model.addAttribute("estadoCuenta", user.getEstadoCuenta());
        return "Aprendiz/aprendiz";
    }

    @GetMapping("/excusas")
    public String mostrarExcusas(Model model) {
        model.addAttribute("excusa", new Excusas());
        model.addAttribute("excusas", excusasService.findAll());
        return "Aprendiz/excusas";
    }

    // =========================== CRUD DE EXCUSAS ===========================

    // Mostrar formulario de nueva excusa
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("excusa", new Excusas());
        return "Aprendiz/excusaForm";
    }

    // Guardar nueva excusa
    @PostMapping("/save")
    public String guardarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
            throws IOException {

        if (!archivo.isEmpty()) {
            Path rutaCarpeta = Paths.get("uploads");
            if (!Files.exists(rutaCarpeta)) {
                Files.createDirectories(rutaCarpeta);
            }

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaArchivo = rutaCarpeta.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
            excusa.setSoporte(nombreArchivo);
        }

        excusasService.save(excusa);
        LOGGER.info("Excusa guardada con ID {}", excusa.getId());
        return "redirect:/Aprendiz/excusas";
    }

    // Editar excusa existente
    @GetMapping("/edit/{id}")
    public String editarExcusa(@PathVariable Integer id, Model model) {
        Excusas excusa = excusasService.findById(id)
                .orElseThrow(() -> new RuntimeException("Excusa no encontrada"));
        model.addAttribute("excusa", excusa);
        return "Aprendiz/excusaForm";
    }

    // Actualizar excusa
    @PostMapping("/update")
    public String actualizarExcusa(@ModelAttribute Excusas excusa, @RequestParam("img") MultipartFile archivo)
            throws IOException {

        Excusas existente = excusasService.findById(excusa.getId())
                .orElseThrow(() -> new RuntimeException("Excusa no encontrada"));

        existente.setMotivo(excusa.getMotivo());
        existente.setFecha(excusa.getFecha());
        existente.setFicha(excusa.getFicha());

        if (!archivo.isEmpty()) {
            Path rutaCarpeta = Paths.get("uploads");
            if (!Files.exists(rutaCarpeta)) {
                Files.createDirectories(rutaCarpeta);
            }

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaArchivo = rutaCarpeta.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
            existente.setSoporte(nombreArchivo);
        }

        excusasService.save(existente);
        LOGGER.info("Excusa actualizada ID {}", existente.getId());
        return "redirect:/Aprendiz/excusas";
    }

    // Eliminar excusa
    @GetMapping("/delete/{id}")
    public String eliminarExcusa(@PathVariable Integer id) {
        excusasService.delete(id);
        LOGGER.info("Excusa eliminada ID {}", id);
        return "redirect:/Aprendiz/excusas";
    }

    // =========================== INFORMACIÓN DE USUARIO ===========================

    @GetMapping("/infouser")
    public String mostrarInfo(Model model) {
        Usuario user = usuarioService.findAll().get(0);
        model.addAttribute("usuario", user);
        model.addAttribute("ficha", user.getFicha());
        model.addAttribute("estadoCuenta", user.getEstadoCuenta());
        return "Aprendiz/aprendiz";
    }
}
