package com.sena.techaccess.apis;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.service.IAccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accesos")
@CrossOrigin(origins = "*")
public class ApiAcceso {

    @Autowired
    private IAccesoService accesoService;

    // ==================== CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<Acceso>> getAll() {
        List<Acceso> accesos = accesoService.findAllWithUsuario();
        return ResponseEntity.ok(accesos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acceso> getById(@PathVariable Integer id) {
        Optional<Acceso> accesoOpt = accesoService.findById(id);
        return accesoOpt.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Acceso> create(@RequestBody Acceso acceso) {
        Acceso savedAcceso = accesoService.save(acceso);
        return ResponseEntity.ok(savedAcceso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acceso> update(@PathVariable Integer id, @RequestBody Acceso acceso) {
        Optional<Acceso> accesoOpt = accesoService.findById(id);
        if (accesoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        acceso.setId(id);
        Acceso updatedAcceso = accesoService.save(acceso);
        return ResponseEntity.ok(updatedAcceso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<Acceso> accesoOpt = accesoService.findById(id);
        if (accesoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        accesoService.delete(id);
        return ResponseEntity.ok("Acceso eliminado correctamente");
    }

    // ==================== ENDPOINTS ESPECÍFICOS (QUE SÍ EXISTEN) ====================

    @GetMapping("/ultimo/{usuarioId}")
    public ResponseEntity<Acceso> getUltimoAcceso(@PathVariable Integer usuarioId) {
        Acceso acceso = accesoService.findUltimoAcceso(usuarioId);
        if (acceso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acceso);
    }

    @GetMapping("/ultimo-activo/{usuarioId}")
    public ResponseEntity<Acceso> getUltimoAccesoActivo(@PathVariable Integer usuarioId) {
        Acceso acceso = accesoService.findUltimoAccesoActivo(usuarioId);
        if (acceso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acceso);
    }

    @GetMapping("/todos-ultimos")
    public ResponseEntity<Map<Integer, Acceso>> getLatestAccessForAllUsers() {
        Map<Integer, Acceso> mapa = accesoService.findLatestAccessForAllUsers();
        return ResponseEntity.ok(mapa);
    }

    @PostMapping("/registrar/{usuarioId}")
    public ResponseEntity<Acceso> registrarAcceso(@PathVariable Integer usuarioId) {
        Acceso acceso = accesoService.registrarAcceso(usuarioId);
        return ResponseEntity.ok(acceso);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<Acceso>> filtrarUsuariosIngresos(
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String ficha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIngreso,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEgreso,
            Pageable pageable) {
        
        Page<Acceso> pagina = accesoService.filtrarUsuariosIngresos(
            usuarioId, nombre, documento, rol, ficha, fechaIngreso, fechaEgreso, pageable);
        
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/ingreso/{horaIngreso}")
    public ResponseEntity<Acceso> getByHoraIngreso(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaIngreso) {
        Acceso acceso = accesoService.findByHoraIngreso(horaIngreso);
        if (acceso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acceso);
    }

    @GetMapping("/egreso/{horaEgreso}")
    public ResponseEntity<List<Acceso>> getByHoraEgreso(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horaEgreso) {
        List<Acceso> accesos = accesoService.findByHoraEgreso(horaEgreso);
        return ResponseEntity.ok(accesos);
    }

    // ==================== ENDPOINTS SIMPLES CON FILTROS BÁSICOS ====================

    @GetMapping("/activos")
    public ResponseEntity<List<Acceso>> getAccesosActivos() {
        List<Acceso> todos = accesoService.findAllWithUsuario();
        List<Acceso> activos = todos.stream()
            .filter(a -> a.getHoraEgreso() == null)
            .toList();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<Acceso>> getAccesosHoy() {
        LocalDateTime hoy = LocalDateTime.now();
        LocalDateTime inicioDia = hoy.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finDia = hoy.withHour(23).withMinute(59).withSecond(59);
        
        List<Acceso> todos = accesoService.findAllWithUsuario();
        List<Acceso> hoyAccesos = todos.stream()
            .filter(a -> a.getHoraIngreso() != null && 
                        !a.getHoraIngreso().isBefore(inicioDia) &&
                        !a.getHoraIngreso().isAfter(finDia))
            .toList();
        
        return ResponseEntity.ok(hoyAccesos);
    }

    @GetMapping("/usuario/{usuarioId}/recientes")
    public ResponseEntity<List<Acceso>> getAccesosRecientesPorUsuario(@PathVariable Integer usuarioId) {
        // Este método no existe directamente, pero podemos filtrar
        List<Acceso> todos = accesoService.findAllWithUsuario();
        List<Acceso> usuarioAccesos = todos.stream()
            .filter(a -> a.getUsuario() != null && 
                        a.getUsuario().getId().equals(usuarioId))
            .sorted((a1, a2) -> a2.getHoraIngreso().compareTo(a1.getHoraIngreso()))
            .limit(10)
            .toList();
        
        return ResponseEntity.ok(usuarioAccesos);
    }

    // ==================== ESTADÍSTICAS ====================

    @GetMapping("/estadisticas/hoy")
    public ResponseEntity<Map<String, Object>> getEstadisticasHoy() {
        List<Acceso> hoyAccesos = getAccesosHoy().getBody();
        List<Acceso> activos = getAccesosActivos().getBody();
        
        Map<String, Object> estadisticas = Map.of(
            "totalHoy", hoyAccesos != null ? hoyAccesos.size() : 0,
            "activosAhora", activos != null ? activos.size() : 0,
            "timestamp", LocalDateTime.now()
        );
        
        return ResponseEntity.ok(estadisticas);
    }

    // ==================== ENDPOINTS DE PRUEBA/HEALTH ====================

    @GetMapping("/test/conexion")
    public ResponseEntity<String> testConexion() {
        return ResponseEntity.ok("API Acceso funcionando - " + LocalDateTime.now());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        List<Acceso> todos = accesoService.findAllWithUsuario();
        return ResponseEntity.ok((long) todos.size());
    }
}