package com.sena.techaccess.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "acceso")
public class Acceso {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idacceso;
	
	@Temporal(TemporalType.TIMESTAMP)//Almacena fecha/hora en la BD
	private Date horaIngreso;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaEgreso;
	
	private String dispositivos;

	
	@OneToOne(mappedBy = "acceso", fetch = FetchType.LAZY)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	private EstadoPermanencia estadoPermanencia; // detalleacceso

	@ManyToOne(fetch = FetchType.LAZY)
	private Vigilancia vigilancia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AreaJobs areaJobs;

	// constructor vacio
	public Acceso() {
	}

	// constructor con campos
	public Acceso(Integer idacceso, Date horaIngreso, Date horaEgreso, String dispositivos) {
		super();
		this.idacceso = idacceso;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.dispositivos = dispositivos;
	}

	// getters and setters
	public Integer getIdacceso() {
		return idacceso;
	}

	public void setIdacceso(Integer idacceso) {
		this.idacceso = idacceso;
	}

	public Date getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(Date horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public Date getHoraEgreso() {
		return horaEgreso;
	}

	public void setHoraEgreso(Date horaEgreso) {
		this.horaEgreso = horaEgreso;
	}

	public String getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(String dispositivos) {
		this.dispositivos = dispositivos;
	}

	@Override
	public String toString() {
		return "Acceso [idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
				+ ", dispositivos=" + dispositivos + "]";
	}

}