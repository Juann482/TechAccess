package com.sena.techaccess.apis;

import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.service.IFichaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fichas")
@CrossOrigin(origins = "*")
public class ApiFichaController {

    @Autowired
    private IFichaService fichaService;

    // ==================== CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<Ficha>> getAll() {
        List<Ficha> fichas = fichaService.findAll();
        return ResponseEntity.ok(fichas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ficha> getById(@PathVariable Integer id) {
        // CAMBIADO: Usando get() en lugar de findById()
        Optional<Ficha> fichaOpt = fichaService.get(id);
        return fichaOpt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ficha> create(@RequestBody Ficha ficha) {
        Ficha savedFicha = fichaService.save(ficha);
        return ResponseEntity.ok(savedFicha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ficha> update(@PathVariable Integer id, @RequestBody Ficha ficha) {
        // CAMBIADO: Usando get() en lugar de findById()
        Optional<Ficha> fichaOpt = fichaService.get(id);
        if (fichaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        ficha.setId(id);
        Ficha updatedFicha = fichaService.save(ficha);
        return ResponseEntity.ok(updatedFicha);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        // CAMBIADO: Usando get() en lugar de findById()
        Optional<Ficha> fichaOpt = fichaService.get(id);
        if (fichaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        fichaService.delete(id);
        return ResponseEntity.ok("Ficha eliminada correctamente");
    }

    // ==================== BÚSQUEDAS ESPECÍFICAS ====================

    @GetMapping("/programa/{nombrePrograma}")
    public ResponseEntity<Ficha> getByNombrePrograma(@PathVariable String nombrePrograma) {
        Ficha ficha = fichaService.findByNombrePrograma(nombrePrograma);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ficha);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ficha>> getByEstado(@PathVariable String estado) {
        List<Ficha> fichas = fichaService.findByEstado(estado);
        return ResponseEntity.ok(fichas);
    }

    @GetMapping("/jornada/{jornada}")
    public ResponseEntity<Ficha> getByJornada(@PathVariable String jornada) {
        Ficha ficha = fichaService.findByJornada(jornada);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ficha);
    }

    // ==================== FILTRADO CON PAGINACIÓN ====================

    @GetMapping("/filtrar")
    public ResponseEntity<Page<Ficha>> filtrarFichas(
            @RequestParam(required = false) String nombrePrograma,
            @RequestParam(required = false) String jornada,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer numFicha,
            Pageable pageable) {
        
        Page<Ficha> pagina = fichaService.filtrarFicha(nombrePrograma, jornada, estado, numFicha, pageable);
        return ResponseEntity.ok(pagina);
    }

    // ==================== OPERACIONES ESPECIALES ====================

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ficha> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {
        
        // CAMBIADO: Usando get() en lugar de findById()
        Optional<Ficha> fichaOpt = fichaService.get(id);
        if (fichaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Ficha ficha = fichaOpt.get();
        ficha.setEstado(estado);
        Ficha updatedFicha = fichaService.save(ficha);
        return ResponseEntity.ok(updatedFicha);
    }

    // ==================== ENDPOINTS SIMPLIFICADOS ====================

    @GetMapping("/activas")
    public ResponseEntity<List<Ficha>> getFichasActivas() {
        return ResponseEntity.ok(fichaService.findByEstado("Activa"));
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<Ficha>> getFichasInactivas() {
        return ResponseEntity.ok(fichaService.findByEstado("Inactiva"));
    }

    @GetMapping("/buscar/numero")
    public ResponseEntity<?> buscarPorNumeroFicha(@RequestParam Integer numFicha) {
        List<Ficha> todasFichas = fichaService.findAll();
        for (Ficha ficha : todasFichas) {
            if (ficha.getNumFicha() != null && ficha.getNumFicha().equals(numFicha)) {
                return ResponseEntity.ok(ficha);
            }
        }
        return ResponseEntity.status(404).body("Ficha no encontrada con número: " + numFicha);
    }

    
}