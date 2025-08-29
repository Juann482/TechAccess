package com.sena.techaccess.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vigilancia")
public class Vigilancia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVigilacia;
	private Date horaIngreso;
	private Date horaEgreso;
	private String turno;

	@ManyToOne
	private EstadoPermanencia estadopermanencia;

	@OneToOne
	private Usuario usuario;

	@ManyToOne
	private Acceso acceso;

	// constructor vacío
	public Vigilancia() {
	}

	// constructor con campos
	public Vigilancia(Integer idVigilacia, Date horaIngreso, Date horaEgreso, String turno) {
		super();
		this.idVigilacia = idVigilacia;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.turno = turno;
	}

	// getters and setters
	public Integer getIdVigilacia() {
		return idVigilacia;
	}

	public void setIdVigilacia(Integer idVigilacia) {
		this.idVigilacia = idVigilacia;
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

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public EstadoPermanencia getEstadopermanencia() {
		return estadopermanencia;
	}

	public void setEstadopermanencia(EstadoPermanencia estadopermanencia) {
		this.estadopermanencia = estadopermanencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Acceso getAcceso() {
		return acceso;
	}

	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	// toString
	@Override
	public String toString() {
		return "Vigilancia [idVigilacia=" + idVigilacia + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
				+ ", turno=" + turno + "]";
	}

}