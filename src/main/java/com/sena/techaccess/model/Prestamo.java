package com.sena.techaccess.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    
    @ManyToOne
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    private Bibliotecario bibliotecario;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(nullable = false)
    private LocalDate fechaPrestamo;
    
    private LocalDate fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;
    
    public enum EstadoPrestamo {
        ACTIVO, DEVUELTO, VENCIDO
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    // Métodos de utilidad
    @Override
    public String toString() {
        return "Prestamo{" +
               "id=" + id +
               ", libro=" + (libro != null ? libro.getClass() : null) +
               ", bibliotecario=" + (bibliotecario != null ? bibliotecario.getId() : null) +
               ", usuario=" + (usuario != null ? usuario.getId() : null) +
               ", fechaPrestamo=" + fechaPrestamo +
               ", fechaDevolucion=" + fechaDevolucion +
               ", estado=" + estado +
               '}';
    }

    public void registrarDevolucion() {
        this.estado = EstadoPrestamo.DEVUELTO;
        this.fechaDevolucion = LocalDate.now();
    }

    public boolean estaVencido() {
        return this.estado == EstadoPrestamo.VENCIDO || 
              (this.estado == EstadoPrestamo.ACTIVO && 
               LocalDate.now().isAfter(this.fechaPrestamo.plusDays(15))); // Ejemplo: 15 días de préstamo
    }
}