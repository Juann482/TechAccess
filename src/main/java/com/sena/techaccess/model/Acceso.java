package com.sena.techaccess.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acceso")
public class Acceso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idacceso;

	@Column(name = "horaIngreso")
	private LocalDateTime horaIngreso;

	@Column(name = "horaEgreso")
	private LocalDateTime horaEgreso;

	// Sección enlazada con Dispositivo
	@OneToOne
	@JoinColumn(name = "idDisp")
	private Dispositivo dispositivo;

	// Sección enlazada con Usuario
	@OneToOne(mappedBy = "acceso", fetch = FetchType.LAZY)
	private Usuario usuario;

	// Sección enlazada con EstadoPermanencia
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idestadoPermanencia")
	private EstadoPermanencia estadoPermanencia;

	/*
	 * / Sección enlazada con Área
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "idarea") private AreaJobs area;
	 */

	@ManyToOne
	@JoinColumn(name = "id_vigilancia")
	private Vigilancia vigilancia;

	public Acceso(Integer idacceso, LocalDateTime horaIngreso, LocalDateTime horaEgreso, Dispositivo dispositivo,
			Usuario usuario, EstadoPermanencia estadoPermanencia, AreaJobs area) {
		this.idacceso = idacceso;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.dispositivo = dispositivo;
		this.usuario = usuario;
		this.estadoPermanencia = estadoPermanencia;
		// this.area = area;
	}

	public Acceso() {

	}

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

	/*
	 * public AreaJobs getArea() { return area; }
	 */

	/*
	 * public void setArea(AreaJobs area) { this.area = area; }
	 */

	@Override
	public String toString() {
		return "Acceso [idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
				+ ", dispositivo=" + dispositivo + ", usuario=" + usuario + ", estadoPermanencia=" + estadoPermanencia
				+ "]";
	}

}
