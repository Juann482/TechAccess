package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ CORREGIDO
	private Long id;

	private String hora;
	private String lunes;
	private String martes;
	private String miercoles;
	private String jueves;
	private String viernes;

	
	public Horario() {
	}

	public Horario(Long id, String hora, String lunes, String martes, String miercoles, String jueves, String viernes) {
		this.id = id;
		this.hora = hora;
		this.lunes = lunes;
		this.martes = martes;
		this.miercoles = miercoles;
		this.jueves = jueves;
		this.viernes = viernes;
	}

	// 🔹 Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getLunes() {
		return lunes;
	}

	public void setLunes(String lunes) {
		this.lunes = lunes;
	}

	public String getMartes() {
		return martes;
	}

	public void setMartes(String martes) {
		this.martes = martes;
	}

	public String getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(String miercoles) {
		this.miercoles = miercoles;
	}

	public String getJueves() {
		return jueves;
	}

	public void setJueves(String jueves) {
		this.jueves = jueves;
	}

	public String getViernes() {
		return viernes;
	}

	public void setViernes(String viernes) {
		this.viernes = viernes;
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", hora=" + hora + ", lunes=" + lunes + ", martes=" + martes + ", miercoles="
				+ miercoles + ", jueves=" + jueves + ", viernes=" + viernes + "]";
	}
}
