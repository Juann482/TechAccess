package com.sena.techaccess.controller;

import com.sena.techaccess.model.SuperAdministrador;
import com.sena.techaccess.service.ISuperadminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/superadmin/superadmins")
public class SuperAdministradorController {

    @Autowired
    private ISuperadminService superadminService;

    @GetMapping("")
    public String listarSuperAdmins(Model model) {
        List<SuperAdministrador> superAdmins = superadminService.findAll();
        model.addAttribute("superAdmins", superAdmins);
        return "superadmin/superadmins/list";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("superAdmin", new SuperAdministrador());
        return "superadmin/superadmins/form";
    }

    @PostMapping("/guardar")
    public String guardarSuperAdmin(@ModelAttribute SuperAdministrador superAdmin, RedirectAttributes redirect) {
        if (superadminService.existsByEmail(superAdmin.getEmail())) {
            redirect.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/superadmin/superadmins/crear";
        }
        if (superadminService.existsByDocumento(superAdmin.getDocumento())) {
            redirect.addFlashAttribute("error", "El documento ya está registrado");
            return "redirect:/superadmin/superadmins/crear";
        }
        superadminService.save(superAdmin);
        redirect.addFlashAttribute("success", "SuperAdmin creado exitosamente");
        return "redirect:/superadmin/superadmins";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        SuperAdministrador superAdmin = superadminService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("superAdmin", superAdmin);
        return "superadmin/superadmins/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarSuperAdmin(@PathVariable Integer id, @ModelAttribute SuperAdministrador superAdmin, RedirectAttributes redirect) {
        SuperAdministrador existente = superadminService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        if (!existente.getEmail().equals(superAdmin.getEmail()) && superadminService.existsByEmail(superAdmin.getEmail())) {
            redirect.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/superadmin/superadmins/editar/" + id;
        }
        
        if (!existente.getDocumento().equals(superAdmin.getDocumento()) && superadminService.existsByDocumento(superAdmin.getDocumento())) {
            redirect.addFlashAttribute("error", "El documento ya está registrado");
            return "redirect:/superadmin/superadmins/editar/" + id;
        }
        
        superAdmin.setId(id);
        superadminService.save(superAdmin);
        redirect.addFlashAttribute("success", "SuperAdmin actualizado exitosamente");
        return "redirect:/superadmin/superadmins";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSuperAdmin(@PathVariable Integer id, RedirectAttributes redirect) {
        if (superadminService.findById(id).isPresent()) {
            superadminService.deleteById(id);
            redirect.addFlashAttribute("success", "SuperAdmin eliminado exitosamente");
        } else {
            redirect.addFlashAttribute("error", "SuperAdmin no encontrado");
        }
        return "redirect:/superadmin/superadmins";
    }

    @GetMapping("/buscar")
    public String buscarSuperAdmins(@RequestParam(required = false) String nombre, Model model) {
        List<SuperAdministrador> superAdmins;
        if (nombre != null && !nombre.trim().isEmpty()) {
            superAdmins = superadminService.findByNombreContaining(nombre);
        } else {
            superAdmins = superadminService.findAll();
        }
        model.addAttribute("superAdmins", superAdmins);
        model.addAttribute("nombreBuscado", nombre);
        return "superadmin/superadmins/list";
    }
}