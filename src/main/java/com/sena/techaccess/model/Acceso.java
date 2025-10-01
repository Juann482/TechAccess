package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "acceso")
public class Acceso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idacceso;

	private Integer horaIngreso;
	private Integer horaEgreso;

	private String dispositivos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Permanencia")
	private EstadoPermanencia estadoPermanencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vigilancia_idvigilancia")
	private Vigilancia vigilancia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaJobs_idarea")
	private AreaJobs areaJobs;

	public Acceso() {
	}

	public Acceso(Integer idacceso, Integer horaIngreso, Integer horaEgreso, String dispositivos) {
		this.idacceso = idacceso;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.dispositivos = dispositivos;
	}

	public Integer getIdacceso() {
		return idacceso;
	}

	public void setIdacceso(Integer idacceso) {
		this.idacceso = idacceso;
	}

	public Integer getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(Integer horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public Integer getHoraEgreso() {
		return horaEgreso;
	}

	public void setHoraEgreso(Integer horaEgreso) {
		this.horaEgreso = horaEgreso;
	}

	public String getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(String dispositivos) {
		this.dispositivos = dispositivos;
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

	public AreaJobs getAreaJobs() {
		return areaJobs;
	}

	public void setAreaJobs(AreaJobs areaJobs) {
		this.areaJobs = areaJobs;
	}

	@Override
	public String toString() {
		return "Acceso [idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
				+ ", dispositivos=" + dispositivos + "]";
	}
}
