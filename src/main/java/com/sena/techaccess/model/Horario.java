package com.sena.techaccess.model;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ CORREGIDO
	private Long id;
	private Time horaInicio;
	private Time horaFin;
	
	public Horario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Horario(Long id, Time horaInicio, Time horaFin) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Time getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Time horaFin) {
		this.horaFin = horaFin;
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + "]";
	}
	
	
	
}

	