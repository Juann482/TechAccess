package com.sena.techaccess.apis;

import com.sena.techaccess.model.DispositivoVisit;
import com.sena.techaccess.service.IDispositivoVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dispositivos-visit")
@CrossOrigin(origins = "*")
public class ApiDispositivoVisitController {

    @Autowired
    private IDispositivoVisitService dispositivoVisitService;

    // ==================== CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<DispositivoVisit>> getAll() {
        List<DispositivoVisit> dispositivos = dispositivoVisitService.findAll();
        return ResponseEntity.ok(dispositivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoVisit> getById(@PathVariable Integer id) {
        Optional<DispositivoVisit> dispositivoOpt = dispositivoVisitService.get(id);
        return dispositivoOpt.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DispositivoVisit> create(@RequestBody DispositivoVisit dispositivoVisit) {
        DispositivoVisit savedDispositivo = dispositivoVisitService.save(dispositivoVisit);
        return ResponseEntity.ok(savedDispositivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoVisit> update(@PathVariable Integer id, @RequestBody DispositivoVisit dispositivoVisit) {
        Optional<DispositivoVisit> dispositivoOpt = dispositivoVisitService.get(id);
        if (dispositivoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        dispositivoVisit.setId(id);
        DispositivoVisit updatedDispositivo = dispositivoVisitService.save(dispositivoVisit);
        return ResponseEntity.ok(updatedDispositivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<DispositivoVisit> dispositivoOpt = dispositivoVisitService.get(id);
        if (dispositivoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        dispositivoVisitService.delete(id);
        return ResponseEntity.ok("Dispositivo visitante eliminado correctamente");
    }

    // ==================== BÚSQUEDAS ESPECÍFICAS ====================

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<DispositivoVisit> getByTipo(@PathVariable String tipo) {
        DispositivoVisit dispositivo = dispositivoVisitService.findByTipo(tipo);
        if (dispositivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dispositivo);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<DispositivoVisit> getByMarca(@PathVariable String marca) {
        DispositivoVisit dispositivo = dispositivoVisitService.findByMarca(marca);
        if (dispositivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dispositivo);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<DispositivoVisit> getByColor(@PathVariable String color) {
        DispositivoVisit dispositivo = dispositivoVisitService.findByColor(color);
        if (dispositivo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dispositivo);
    }

    // ==================== BÚSQUEDAS CON FILTROS MÚLTIPLES ====================

    @GetMapping("/buscar")
    public ResponseEntity<List<DispositivoVisit>> buscarDispositivos(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String color) {
        
        List<DispositivoVisit> todos = dispositivoVisitService.findAll();
        
        // Filtrar según los parámetros proporcionados
        List<DispositivoVisit> resultado = todos.stream()
            .filter(dispositivo -> {
                boolean coincide = true;
                
                if (tipo != null && !tipo.isEmpty()) {
                    coincide = coincide && dispositivo.getTipo() != null && 
                              dispositivo.getTipo().toLowerCase().contains(tipo.toLowerCase());
                }
                
                if (marca != null && !marca.isEmpty()) {
                    coincide = coincide && dispositivo.getMarca() != null && 
                              dispositivo.getMarca().toLowerCase().contains(marca.toLowerCase());
                }
                
                if (color != null && !color.isEmpty()) {
                    coincide = coincide && dispositivo.getColor() != null && 
                              dispositivo.getColor().toLowerCase().contains(color.toLowerCase());
                }
                
                return coincide;
            })
            .toList();
        
        return ResponseEntity.ok(resultado);
    }

    // ==================== USUARIO RELACIONADO ====================

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DispositivoVisit>> getByUsuarioId(@PathVariable Integer usuarioId) {
        List<DispositivoVisit> todos = dispositivoVisitService.findAll();
        
        List<DispositivoVisit> dispositivosUsuario = todos.stream()
            .filter(dispositivo -> dispositivo.getUsuario() != null && 
                                  dispositivo.getUsuario().getId().equals(usuarioId))
            .toList();
        
        return ResponseEntity.ok(dispositivosUsuario);
    }

    // ==================== ENDPOINTS DE ESTADÍSTICAS ====================

    @GetMapping("/estadisticas/tipos")
    public ResponseEntity<?> getEstadisticasPorTipo() {
        List<DispositivoVisit> todos = dispositivoVisitService.findAll();
        
        // Contar por tipo
        var estadisticas = todos.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                DispositivoVisit::getTipo,
                java.util.stream.Collectors.counting()
            ));
        
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadisticas/marcas")
    public ResponseEntity<?> getEstadisticasPorMarca() {
        List<DispositivoVisit> todos = dispositivoVisitService.findAll();
        
        // Contar por marca
        var estadisticas = todos.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                DispositivoVisit::getMarca,
                java.util.stream.Collectors.counting()
            ));
        
        return ResponseEntity.ok(estadisticas);
    }

    // ==================== ENDPOINTS DE PRUEBA ====================

    @GetMapping("/test/conexion")
    public ResponseEntity<String> testConexion() {
        return ResponseEntity.ok("API Dispositivos Visit funcionando - " + 
                               java.time.LocalDateTime.now());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        List<DispositivoVisit> todos = dispositivoVisitService.findAll();
        return ResponseEntity.ok((long) todos.size());
    }
}