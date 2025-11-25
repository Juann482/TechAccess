package com.sena.techaccess.model;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity
@Table(name = "libros")
public class Bibliotecario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 255)
	private String titulo;

	@Column(nullable = false, length = 150)
	private String autor;

	@Column(unique = true, nullable = false, length = 50)
	private String isbn;

	private Integer anioPublicacion;
	
	@Column(length = 100)
	private String editorial;

	@Enumerated(EnumType.STRING)
	private EstadoLibro estado = EstadoLibro.DISPONIBLE;

	public enum EstadoLibro {
		DISPONIBLE, PRESTADO, EN_REPARACION
	}
}