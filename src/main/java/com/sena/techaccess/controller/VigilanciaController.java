package com.sena.techaccess.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IAccesoService;
import com.sena.techaccess.service.IDispositivoService;
import com.sena.techaccess.service.IDispositivoVisitService;
import com.sena.techaccess.service.IUsuarioService;
import com.sena.techaccess.service.IVigilanciaService;

@Controller
@RequestMapping("/Vigilancia")
public class VigilanciaController {

    private final Logger LOGGER = LoggerFactory.getLogger(VigilanciaController.class);

    @Autowired
    private IDispositivoService dispositivoService;

    @Autowired
    private IVigilanciaService vigilanciaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IAccesoService accesoService;

    @Autowired
    private IDispositivoVisitService dispositivoVisitService;

    // ======================= VISTA INGRESOS ===================================== //

    @GetMapping("/Ingreso")
    public String usuariosvigilancia(@RequestParam(required = false) Integer usuarioId,
                                     @RequestParam(required = false) String nombre,
                                     @RequestParam(required = false) String documento,
                                     @RequestParam(required = false) String rol,
                                     @RequestParam(required = false) String ficha,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaIngreso,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaEgreso,
                                     @RequestParam(defaultValue = "0") int page,
                                     Model model) {
        
        Pageable pageable = PageRequest.of(page, 8);
        
        // DEPURACIÓN
        System.out.println("=== FILTROS APLICADOS ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Documento: " + documento);
        System.out.println("Rol: " + rol);
        System.out.println("Ficha: " + ficha);
        System.out.println("Fecha Ingreso: " + fechaIngreso);
        System.out.println("Fecha Egreso: " + fechaEgreso);
        
        Page<Acceso> accesosPage = accesoService.filtrarUsuariosIngresos(
                usuarioId, nombre, documento, rol, ficha, fechaIngreso, fechaEgreso, pageable
        );
        
        System.out.println("Total registros encontrados: " + accesosPage.getTotalElements());
        System.out.println("Total páginas: " + accesosPage.getTotalPages());
        System.out.println("Registros en esta página: " + accesosPage.getContent().size());
        
        model.addAttribute("accesos", accesosPage.getContent());
        model.addAttribute("page", accesosPage);
        
        // Mantener los filtros en el modelo para la paginación
        model.addAttribute("nombre", nombre);
        model.addAttribute("documento", documento);
        model.addAttribute("rol", rol);
        model.addAttribute("ficha", ficha);
        model.addAttribute("fechaIngreso", fechaIngreso);
        model.addAttribute("fechaEgreso", fechaEgreso);
        
        // Calcular páginas para mostrar
        int totalPages = accesosPage.getTotalPages();
        int currentPage = accesosPage.getNumber();
        int maxPagesToShow = 5;
        
        int startPage = Math.max(0, currentPage - maxPagesToShow / 2);
        int endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);
        
        if (endPage - startPage < maxPagesToShow - 1) {
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }
        
        model.addAttribute("pageNumbers", 
            java.util.stream.IntStream.rangeClosed(startPage, endPage).boxed().toList()
        );
        
        return "Vigilancia/Ingresos";
    }

    // ============================================================
    // 🔥 REGISTRO AUTOMÁTICO → INGRESO / EGRESO
    // ============================================================

    @PostMapping("/registrar-scan")
    @ResponseBody
    public String registrarScan(@RequestParam("documento") String documento) {
        Usuario usuario = usuarioService.findByDocumento(documento);
        if (usuario == null) {
            return "Usuario no encontrado";
        }
        Acceso acceso = accesoService.registrarAcceso(usuario.getId());
        return acceso.getHoraEgreso() == null ? "Ingreso registrado correctamente" : "Egreso registrado correctamente";
    }

    // ============================================================
    // MÉTODOS MANUALES
    // ============================================================

    @PostMapping("/registrar-ingreso")
    @ResponseBody
    public String registrarIngreso(@RequestParam("documento") String documento) {
        Usuario usuario = usuarioService.findByDocumento(documento);
        if (usuario == null) {
            return "Usuario no encontrado";
        }

        Acceso ultimoAcceso = accesoService.findUltimoAccesoActivo(usuario.getId());
        if (ultimoAcceso != null && ultimoAcceso.getHoraEgreso() == null) {
            return "El usuario ya tiene un ingreso activo. Debe registrar salida.";
        }

        Acceso nuevo = new Acceso();
        nuevo.setUsuario(usuario);
        nuevo.setHoraIngreso(LocalDateTime.now());
        nuevo.setActividad("Ingresado");
        accesoService.save(nuevo);

        LOGGER.info("Ingreso registrado (manual) -> Usuario {}", usuario.getNombre());
        return "Ingreso registrado correctamente";
    }

    @PostMapping("/registrar-egreso")
    @ResponseBody
    public String registrarEgreso(@RequestParam("documento") String documento) {
        Usuario usuario = usuarioService.findByDocumento(documento);
        if (usuario == null) {
            return "Usuario no encontrado";
        }

        Acceso ultimoAcceso = accesoService.findUltimoAccesoActivo(usuario.getId());
        if (ultimoAcceso == null) {
            return "No hay ingreso activo para este usuario.";
        }

        ultimoAcceso.setHoraEgreso(LocalDateTime.now());
        ultimoAcceso.setActividad("Egresado");
        accesoService.save(ultimoAcceso);

        LOGGER.info("Egreso registrado (manual) -> Usuario {}", usuario.getNombre());
        return "Egreso registrado correctamente";
    }

    // =====================================================================
    // VISITANTES
    // =====================================================================

    @GetMapping("/registro")
    public String historialVisitante(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("dispositivo", new DispositivoVisit());

        List<Usuario> visitantes = usuarioService.findByRol("Visitante");
        
        // Crear una lista de DTOs o Maps
        List<Map<String, Object>> visitantesConUltimoAcceso = new ArrayList<>();
        
        for (Usuario v : visitantes) {
            Acceso ultimo = accesoService.findUltimoAcceso(v.getId());
            
            Map<String, Object> usuarioMap = new HashMap<>();
            usuarioMap.put("usuario", v);
            usuarioMap.put("ultimoAcceso", ultimo);
            
            visitantesConUltimoAcceso.add(usuarioMap);
        }

        model.addAttribute("visitantesH", visitantesConUltimoAcceso);
        return "Vigilancia/Visitantes";
    }

    @PostMapping("/visitanteSave")
    public String registroVisitante(Usuario usuario, DispositivoVisit dispositivo) {
        usuario.setRol("Visitante");
        usuario.setEstadoCuenta("Activo");
        usuarioService.save(usuario);

        if (dispositivo.getTipo() != null && !dispositivo.getTipo().equals("--Tipo--")) {
            dispositivo.setUsuario(usuario);
            dispositivoVisitService.save(dispositivo);
        }

        Acceso acceso = new Acceso();
        acceso.setUsuario(usuario);
        acceso.setHoraIngreso(LocalDateTime.now());
        acceso.setActividad("Ingresado");
        accesoService.save(acceso);

        return "redirect:/Vigilancia/registro";
    }

    @PostMapping("/visitante-egreso")
    @ResponseBody
    public String registrarEgresoVisitante(@RequestParam("id") Integer usuarioId) {
        Usuario usuario = usuarioService.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return "Usuario no encontrado";
        }

        Acceso ultimoAcceso = accesoService.findUltimoAccesoActivo(usuarioId);
        if (ultimoAcceso == null) {
            return "No hay ingreso activo para este visitante.";
        }

        ultimoAcceso.setHoraEgreso(LocalDateTime.now());
        ultimoAcceso.setActividad("Egresado");
        accesoService.save(ultimoAcceso);

        return "OK";
    }

    // =====================================================================
    // DISPOSITIVOS VISITANTES
    // =====================================================================

    @GetMapping("/DispositivosVisit")
    public String dispositivosUser(Model model) {
        model.addAttribute("dispositivosVisit", dispositivoVisitService.findAll());
        model.addAttribute("dispoVisit", new DispositivoVisit());
        return "Vigilancia/Dispositivos";
    }

    @GetMapping("/DeleteDV/{id}")
    public String EliminarDispoV(@PathVariable Integer id) {
        dispositivoVisitService.delete(id);
        return "redirect:/Vigilancia/DispositivosVisit";
    }

    // =====================================================================
    // DISPOSITIVOS FUNCIONARIOS
    // =====================================================================

    @GetMapping("/DispositivosUSER")
    public String dispositivosUSER(Model model) {
        model.addAttribute("DispoUSER", new Usuario());
        return "Vigilancia/RegistroDU";
    }

    @PostMapping("/GuardarDispoUSER")
    public String guardarDispositivoU(Dispositivo dispositivo) {
        dispositivoService.save(dispositivo);
        return "redirect:/Vigilancia/Ingreso";
    }

    @GetMapping("/RDU")
    public String ListadoDispoUSER(Model model) {
        model.addAttribute("Listado", dispositivoService.findAll());
        model.addAttribute("ListadoDispo", new Dispositivo());
        return "Vigilancia/DispositivosUSER";
    }

    @GetMapping("/DeleteDUSER/{id}")
    public String EliminarDispoUSER(@PathVariable Integer id) {
        dispositivoService.delete(id);
        return "redirect:/Vigilancia/RDU";
    }
}