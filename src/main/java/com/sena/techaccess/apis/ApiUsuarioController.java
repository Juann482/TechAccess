package com.sena.techaccess.apis;

import com.sena.techaccess.model.Usuario;
import com.sena.techaccess.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class ApiUsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // ==================== CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        return usuarioOpt.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(savedUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    // ==================== BÚSQUEDAS ESPECÍFICAS ====================

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Usuario> getByDocumento(@PathVariable String documento) {
        Usuario usuario = usuarioService.findByDocumento(documento);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable String nombre) {
        Usuario usuario = usuarioService.findByNombre(nombre);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getByEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
        return usuarioOpt.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> getByRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.findByRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/estado/{estadoCuenta}")
    public ResponseEntity<List<Usuario>> getByEstado(@PathVariable String estadoCuenta) {
        List<Usuario> usuarios = usuarioService.findByEstadoCuenta(estadoCuenta);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rol/{rol}/estado/{estado}")
    public ResponseEntity<List<Usuario>> getByRolAndEstado(
            @PathVariable String rol,
            @PathVariable String estado) {
        List<Usuario> usuarios = usuarioService.findByRolAndEstadoCuenta(rol, estado);
        return ResponseEntity.ok(usuarios);
    }

    // ==================== FILTRADO CON PAGINACIÓN ====================

    @GetMapping("/filtrar")
    public ResponseEntity<Page<Usuario>> filtrarUsuarios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estado,
            Pageable pageable) {
        
        Page<Usuario> pagina = usuarioService.filtrarUsuarios(nombre, documento, rol, estado, pageable);
        return ResponseEntity.ok(pagina);
    }

    // ==================== FICHA RELACIONADA ====================

    @GetMapping("/ficha/{fichaId}")
    public ResponseEntity<List<Usuario>> getByFichaId(@PathVariable Integer fichaId) {
        List<Usuario> usuarios = usuarioService.findByFichaId(fichaId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/ficha/{fichaId}/pagina")
    public ResponseEntity<Page<Usuario>> getByFichaIdPage(
            @PathVariable Integer fichaId,
            Pageable pageable) {
        Page<Usuario> pagina = usuarioService.findByFichaId(fichaId, pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/ficha/{fichaId}/filtrar")
    public ResponseEntity<Page<Usuario>> filtrarUsuariosEnFicha(
            @PathVariable Integer fichaId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estado,
            Pageable pageable) {
        
        Page<Usuario> pagina = usuarioService.filtrarUsuariosEnFicha(
            fichaId, nombre, documento, rol, estado, pageable);
        return ResponseEntity.ok(pagina);
    }

    // ==================== DASHBOARD / ESTADÍSTICAS ====================

    @GetMapping("/dashboard/total")
    public ResponseEntity<Integer> getTotalUsuarios() {
        int total = usuarioService.totalUsuarios();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/dashboard/activos")
    public ResponseEntity<Integer> getUsuariosActivos() {
        int activos = usuarioService.Activos();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/dashboard/inactivos")
    public ResponseEntity<Integer> getUsuariosInactivos() {
        int inactivos = usuarioService.Inactivos();
        return ResponseEntity.ok(inactivos);
    }

    @GetMapping("/dashboard/por-rol")
    public ResponseEntity<Map<String, Integer>> getEstadisticasPorRol() {
        Map<String, Integer> estadisticas = Map.of(
            "aprendices", usuarioService.Aprendiz(),
            "aprendicesActivos", usuarioService.AprendizAct(),
            "aprendicesInactivos", usuarioService.AprendizIN(),
            "instructores", usuarioService.Instructor(),
            "instructoresActivos", usuarioService.InstructorAc(),
            "instructoresInactivos", usuarioService.InstructorIN(),
            "visitantes", usuarioService.Visitante(),
            "visitantesActivos", usuarioService.VisitantesAc(),
            "visitantesInactivos", usuarioService.VisitantesIN()
        );
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/dashboard/activos-por-rol")
    public ResponseEntity<Map<String, Long>> getUsuariosActivosPorRol() {
        Map<String, Long> mapa = usuarioService.obtenerUsuariosActivosPorRol();
        return ResponseEntity.ok(mapa);
    }

    // ==================== OPERACIONES ESPECIALES ====================

    @PostMapping("/batch")
    public ResponseEntity<String> saveBatch(@RequestBody List<Usuario> usuarios) {
        usuarioService.saveAll(usuarios);
        return ResponseEntity.ok("Usuarios guardados en lote");
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {
        
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        usuarioService.actualizarEstado(id, estado);
        return ResponseEntity.ok("Estado actualizado correctamente");
    }

    @GetMapping("/rol/{rol}/con-accesos")
    public ResponseEntity<List<Usuario>> getByRolWithAccesos(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.findByRolWithAccesos(rol);
        return ResponseEntity.ok(usuarios);
    }

    // ==================== LOGIN / AUTENTICACIÓN ====================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
        
        // Eliminar password de la respuesta por seguridad
        usuario.setPassword(null);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(
            @RequestParam Integer id,
            @RequestParam String passwordActual,
            @RequestParam String passwordNueva) {
        
        Optional<Usuario> usuarioOpt = usuarioService.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        if (!usuario.getPassword().equals(passwordActual)) {
            return ResponseEntity.status(401).body("Contraseña actual incorrecta");
        }
        
        usuario.setPassword(passwordNueva);
        usuarioService.save(usuario);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
}