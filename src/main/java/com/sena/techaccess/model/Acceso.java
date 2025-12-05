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
	private Integer idacceso;

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

	// Estado Permanencia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado_permanencia")
	private EstadoPermanencia estadoPermanencia;

	// Vigilancia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vigilancia")
	private Vigilancia vigilancia;

	// Constructores
	public Acceso() {
	}

	public Acceso(Integer idacceso, LocalDateTime horaIngreso, LocalDateTime horaEgreso, Dispositivo dispositivo,
			Usuario usuario, EstadoPermanencia estadoPermanencia, Vigilancia vigilancia) {
		this.idacceso = idacceso;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.dispositivo = dispositivo;
		this.usuario = usuario;
		this.estadoPermanencia = estadoPermanencia;
		this.vigilancia = vigilancia;
	}

	// Getters y Setters
	public Integer getIdacceso() {
		return idacceso;
	}

	public void setIdacceso(Integer idacceso) {
		this.idacceso = idacceso;
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

	public EstadoPermanencia getEstadoPermanencia() {
		return estadoPermanencia;
	}

	public void setEstadoPermanencia(EstadoPermanencia estadoPermanencia) {
		this.estadoPermanencia = estadoPermanencia;
	}

	public Vigilancia getVigilancia() {
		return vigilancia;
	}

	public void setVigilancia(Vigilancia vigilancia) {
		this.vigilancia = vigilancia;
	}

	@Override
	public String toString() {
		return "Acceso{" + "idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso + '}';
	}

	public Object getSalida() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getEntrada() {
		// TODO Auto-generated method stub
		return null;
	}
}