package com.sena.techaccess.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "acceso")
public class Acceso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String actividad;

	@Column(name = "horaIngreso")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a", timezone = "America/Bogota")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
	private LocalDateTime horaIngreso;

	@Column(name = "horaEgreso")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a", timezone = "America/Bogota")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
	private LocalDateTime horaEgreso;

	// Relación con Dispositivo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_disp") // Asegúrate de que este sea el nombre correcto en tu base de datos
	private Dispositivo dispositivo;

	// Relación con Usuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	// Vigilancia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vigilancia")
	private Vigilancia vigilancia;

	// Constructores
	public Acceso() {
	}

	public Acceso(Integer id, LocalDateTime horaIngreso, LocalDateTime horaEgreso, Dispositivo dispositivo,
			Usuario usuario, String actividad, Vigilancia vigilancia) {
		this.id = id;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.dispositivo = dispositivo;
		this.usuario = usuario;
		this.actividad = actividad;
		this.vigilancia = vigilancia;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(LocalDateTime horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public LocalDateTime getHoraEgreso() {
		return horaEgreso;
	}

	public void setHoraEgreso(LocalDateTime horaEgreso) {
		this.horaEgreso = horaEgreso;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Vigilancia getVigilancia() {
		return vigilancia;
	}

	public void setVigilancia(Vigilancia vigilancia) {
		this.vigilancia = vigilancia;
	}

	@Override
	public String toString() {
		return "Acceso{" + "id=" + id + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso + '}';
	}

	public Object getSalida() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getEntrada() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getIdacceso() {
		// TODO Auto-generated method stub
		return null;
	}
}