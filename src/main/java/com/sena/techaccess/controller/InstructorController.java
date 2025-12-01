package com.sena.techaccess.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Permisos;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IFichaService;
import com.sena.techaccess.service.IPermisosService;
import com.sena.techaccess.service.IUsuarioService;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private IAccesoService accesoService;

    @Autowired
    private IFichaService fichaService;

    @Autowired
    private IPermisosService permisosService;
    
    @Autowired
    private IUsuarioService usuarioService;

    // instructor PRINCIPAL/inicio
    
    @GetMapping("/Inicio")
    public String vistaPrincipal(Model model) {
        try {
            // 1. Retorna APRENDICES 
            List<Usuario> aprendices = usuarioService.findByRol("Aprendiz");
            model.addAttribute("totalAprendices", aprendices.size());
            
            // 2. Entradas de hoy
            long entradasHoy = accesoService.findAll().stream()
                .filter(acceso -> acceso.getHoraIngreso() != null)
                .filter(acceso -> acceso.getHoraIngreso().toLocalDate().equals(LocalDate.now()))
                .count();
            model.addAttribute("entradasHoy", entradasHoy);
            
            // 3. Grupos activos (fichas únicas)
            List<Ficha> fichasActivas = fichaService.findAll();
            model.addAttribute("gruposActivos", fichasActivas.size());
            
            // 4. Alertas (aprendices sin acceso hoy)
            long aprendicesSinAccesoHoy = aprendices.stream()
                .filter(aprendiz -> {
                    if (aprendiz.getAcceso() == null || aprendiz.getAcceso().getHoraIngreso() == null) {
                        return true;
                    }
                    return !aprendiz.getAcceso().getHoraIngreso().toLocalDate().equals(LocalDate.now());
                })
                .count();
            model.addAttribute("alertas", aprendicesSinAccesoHoy);
            
            // 5. Actividad reciente (últimas 5 entradas del día)
            List<Acceso> actividadReciente = accesoService.findAll().stream()
                .filter(acceso -> acceso.getHoraIngreso() != null)
                .filter(acceso -> acceso.getHoraIngreso().toLocalDate().equals(LocalDate.now()))
                .sorted((a1, a2) -> a2.getHoraIngreso().compareTo(a1.getHoraIngreso()))
                .limit(5)
                .collect(Collectors.toList());
            model.addAttribute("actividadReciente", actividadReciente);
            
            // 6. Información del instructor (NRBD)
            Usuario instructor = new Usuario();
            instructor.setNombre("Instructor Principal");
            instructor.setEmail("instructor@sena.edu.co");
            model.addAttribute("usuario", instructor);

        } catch (Exception e) {
            System.out.println("ERROR en vistaPrincipal: " + e.getMessage());
            e.printStackTrace();
            // Valores DEFAULT
            model.addAttribute("totalAprendices", 0);
            model.addAttribute("entradasHoy", 0);
            model.addAttribute("gruposActivos", 0);
            model.addAttribute("alertas", 0);
            model.addAttribute("actividadReciente", List.of());
        }

        return "instructor/instructor";
    }

    // Enlaces con los otros 3 SIDEBAR
    
    @GetMapping("/nvasEntradas")
    public String vistaEntradas(Model model) {
        try {
            List<Acceso> accesos = accesoService.findAll();

            System.out.println("=== DEBUG NUEVAS ENTRADAS ===");
            System.out.println("Número de accesos encontrados: " + accesos.size());

            if (!accesos.isEmpty()) {
                Acceso primerAcceso = accesos.get(0);
                System.out.println("Primer acceso - ID: " + primerAcceso.getIdacceso());
                System.out.println("Usuario: "
                        + (primerAcceso.getUsuario() != null ? primerAcceso.getUsuario().getNombre() : "NULL"));
            }

            if (accesos.isEmpty()) {
                model.addAttribute("accesos", accesos);
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

        int tamañoPagina = 10;
        int maxPaginas = 5;

        List<Permisos> todos = permisosService.findAll();
        java.util.Collections.reverse(todos);

        if (todos.size() > 50) {
            todos = todos.subList(0, 50);
        }

        int inicio = page * tamañoPagina;
        int fin = Math.min(inicio + tamañoPagina, todos.size());

        List<Permisos> permisosPagina = todos.subList(inicio, fin);
        int totalPaginas = (int) Math.ceil((double) todos.size() / tamañoPagina);

        model.addAttribute("permisos", permisosPagina);
        model.addAttribute("totalPermisos", todos.size());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", totalPaginas);

        return "instructor/reportes";
    }
}