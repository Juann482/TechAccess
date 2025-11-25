package com.sena.techaccess.controller;

import com.sena.techaccess.model.Bibliotecario;
import com.sena.techaccess.service.IBibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    @Autowired
    private IBibliotecarioService bibliotecarioService;

    @GetMapping("/Funcionarios")
    public String panelBibliotecario() {
        return "Funcionarios/Bibliotecario";
    }

    @GetMapping
    public String listarBibliotecarios(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String estado,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        Page<Bibliotecario> pagina = bibliotecarioService.listarPaginado(pageable, busqueda, estado);
        model.addAttribute("pagina", pagina);
        model.addAttribute("titulo", "Gestión de Bibliotecarios");
        return "bibliotecarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("bibliotecario", new Bibliotecario());
        model.addAttribute("titulo", "Nuevo Bibliotecario");
        return "bibliotecarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarBibliotecario(Bibliotecario bibliotecario, RedirectAttributes flash) {
        bibliotecarioService.guardar(bibliotecario);
        flash.addFlashAttribute("success", "Bibliotecario guardado con éxito");
        return "redirect:/bibliotecarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, RedirectAttributes flash) {
        Bibliotecario bibliotecario = bibliotecarioService.findById(id);
        if (bibliotecario == null) {
            flash.addFlashAttribute("error", "El bibliotecario no existe");
            return "redirect:/bibliotecarios";
        }
        model.addAttribute("bibliotecario", bibliotecario);
        model.addAttribute("titulo", "Editar Bibliotecario");
        return "bibliotecarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarBibliotecario(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            bibliotecarioService.eliminar(id);
            flash.addFlashAttribute("success", "Bibliotecario eliminado con éxito");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo eliminar el bibliotecario");
        }
        return "redirect:/bibliotecarios";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes flash) {
        try {
            bibliotecarioService.cambiarEstado(id);
            flash.addFlashAttribute("success", "Estado del bibliotecario actualizado");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se pudo cambiar el estado del bibliotecario");
        }
        return "redirect:/bibliotecarios";
    }
}