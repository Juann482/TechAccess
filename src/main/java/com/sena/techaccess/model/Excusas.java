package com.sena.techaccess.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Table(name = "excusas")
public class Excusas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "El motivo es obligatorio")
	private String motivo;

	@NotNull(message = "La descripción es obligatoria")
	private String descripcion;

	@NotNull(message = "La fecha es obligatoria")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	
	@ManyToOne
	@JoinColumn(name = "ficha_id")
	private Ficha ficha;

	private String soporte;

	@Transient
	private MultipartFile soporteFile;

	public Excusas() {
	}

	// 🔹 Constructor con parámetros
	public Excusas(Integer id, String motivo, String descripcion, LocalDate fecha, String soporte,
			MultipartFile soporteFile, Usuario usuario, Ficha ficha) {
		this.id = id;
		this.motivo = motivo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.soporte = soporte;
		this.soporteFile = soporteFile;
		this.usuario = usuario;
		this.ficha = ficha;
	}

	// 🔹 Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public MultipartFile getSoporteFile() {
		return soporteFile;
	}

	public void setSoporteFile(MultipartFile soporteFile) {
		this.soporteFile = soporteFile;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	@Override
	public String toString() {
		return "Excusas{" + "id=" + id + ", motivo='" + motivo + '\'' + ", descripcion='" + descripcion + '\''
				+ ", fecha=" + fecha + ", soporte='" + soporte + '\'' + ", usuario="
				+ (usuario != null ? usuario.getNombre() : "null") + ", ficha="
				+ (ficha != null ? ficha.getNumFicha() : "null") + '}';
	}
}
