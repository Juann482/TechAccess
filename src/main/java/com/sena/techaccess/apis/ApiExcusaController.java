package com.sena.techaccess.apis;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.service.IExcusasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/excusas")
@CrossOrigin(origins = "*")
public class ApiExcusaController {

    @Autowired
    private IExcusasService excusasService;

    // ==================== SOLO CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<Excusas>> getAll() {
        return ResponseEntity.ok(excusasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Excusas> getById(@PathVariable Integer id) {
        Optional<Excusas> excusaOpt = excusasService.findById(id);
        return excusaOpt.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Excusas> create(@RequestBody Excusas excusas) {
        return ResponseEntity.ok(excusasService.save(excusas));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Excusas> update(@PathVariable Integer id, @RequestBody Excusas excusas) {
        if (excusasService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        excusas.setId(id);
        return ResponseEntity.ok(excusasService.save(excusas));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (excusasService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        excusasService.delete(id);
        return ResponseEntity.ok("Excusa eliminada");
    }
}