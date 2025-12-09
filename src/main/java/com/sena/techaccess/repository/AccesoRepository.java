package com.sena.techaccess.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.techaccess.model.Acceso;

public interface AccesoRepository extends JpaRepository<Acceso, Integer> {


    Acceso findByHoraIngreso(LocalDateTime horaIngreso);

    Acceso findByHoraEgreso(LocalDateTime horaEgreso);

    @Query("SELECT a FROM Acceso a JOIN FETCH a.usuario u WHERE u.id = :usuarioId ORDER BY a.horaIngreso DESC")
    List<Acceso> findByUsuarioIdOrderByHoraIngresoDesc(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT a FROM Acceso a JOIN FETCH a.usuario u WHERE u.id = :usuarioId ORDER BY a.horaIngreso DESC")
    Acceso findTopByUsuarioIdOrderByHoraIngresoDesc(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT a FROM Acceso a JOIN FETCH a.usuario u WHERE u.id = :usuarioId AND a.horaEgreso IS NULL ORDER BY a.horaIngreso DESC")
    Acceso findTopByUsuarioIdAndHoraEgresoIsNullOrderByHoraIngresoDesc(@Param("usuarioId") Integer usuarioId);

    @Query("""
        SELECT a FROM Acceso a 
        JOIN FETCH a.usuario u
        WHERE a.horaIngreso IN (
            SELECT MAX(ac.horaIngreso)
            FROM Acceso ac
            WHERE ac.usuario.id = u.id
        )
    """)
    List<Acceso> findLatestAccessForAllUsers();
    
    @Query(value = """
    	    SELECT a FROM Acceso a 
    	    JOIN FETCH a.usuario u
    	    LEFT JOIN u.ficha f
    	    WHERE (:usuarioId IS NULL OR u.id = :usuarioId)
    	    AND (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
    	    AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
    	    AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
    	    AND (:fichaTexto IS NULL OR :fichaTexto = '' OR CAST(f.numFicha AS string) LIKE CONCAT('%', :fichaTexto, '%'))
    	    AND (:fechaIngreso IS NULL OR DATE(a.horaIngreso) = :fechaIngreso)
    	    AND (:fechaEgreso IS NULL OR DATE(a.horaEgreso) = :fechaEgreso)
    	    ORDER BY a.horaIngreso DESC
    	    """,
    	    countQuery = """
    	    SELECT COUNT(a) FROM Acceso a 
    	    JOIN a.usuario u
    	    LEFT JOIN u.ficha f
    	    WHERE (:usuarioId IS NULL OR u.id = :usuarioId)
    	    AND (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
    	    AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
    	    AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
    	    AND (:fichaTexto IS NULL OR :fichaTexto = '' OR CAST(f.numFicha AS string) LIKE CONCAT('%', :fichaTexto, '%'))
    	    AND (:fechaIngreso IS NULL OR DATE(a.horaIngreso) = :fechaIngreso)
    	    AND (:fechaEgreso IS NULL OR DATE(a.horaEgreso) = :fechaEgreso)
    	    """)
    	Page<Acceso> filtrarUsuariosIngresos(
    	    @Param("usuarioId") Integer usuarioId,
    	    @Param("nombre") String nombre,
    	    @Param("documento") String documento,
    	    @Param("rol") String rol,
    	    @Param("fichaTexto") String fichaTexto,  // Cambiado a String para LIKE
    	    @Param("fechaIngreso") LocalDate fechaIngreso,
    	    @Param("fechaEgreso") LocalDate fechaEgreso,
    	    Pageable pageable
    	);
           
    @Query(value = """
        SELECT a FROM Acceso a 
        JOIN FETCH a.usuario u
        WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
        AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
        AND (:fechaIngreso IS NULL OR DATE(a.horaIngreso) = :fechaIngreso)
        AND (:fechaEgreso IS NULL OR DATE(a.horaEgreso) = :fechaEgreso)
        ORDER BY a.horaIngreso DESC
        """,
        countQuery = """
        SELECT COUNT(a) FROM Acceso a 
        JOIN a.usuario u
        WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
        AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
        AND (:fechaIngreso IS NULL OR DATE(a.horaIngreso) = :fechaIngreso)
        AND (:fechaEgreso IS NULL OR DATE(a.horaEgreso) = :fechaEgreso)
        """)
    Page<Acceso> filtrarUsuariosIngresosBasico(
        @Param("nombre") String nombre,
        @Param("documento") String documento,
        @Param("rol") String rol,
        @Param("fechaIngreso") LocalDate fechaIngreso,
        @Param("fechaEgreso") LocalDate fechaEgreso,
        Pageable pageable
    );
    
    @Query("SELECT a FROM Acceso a JOIN FETCH a.usuario ORDER BY a.horaIngreso DESC")
    List<Acceso> findAllWithUsuario();

}