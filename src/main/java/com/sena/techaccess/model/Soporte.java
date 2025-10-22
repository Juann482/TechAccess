package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "soporte")
public class Soporte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String consultante;
	private String email;
	private String peticion;
	private String contenido;
	
	public Soporte() {

	}

	public Soporte(Integer id, String consultante, String email, String peticion, String contenido) {
		super();
		this.id = id;
		this.consultante = consultante;
		this.email = email;
		this.peticion = peticion;
		this.contenido = contenido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConsultante() {
		return consultante;
	}

	public void setConsultante(String consultante) {
		this.consultante = consultante;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPeticion() {
		return peticion;
	}

	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "Soporte [id=" + id + ", consultante=" + consultante + ", email=" + email + ", Peticion=" + peticion
				+ ", contenido=" + contenido + "]";
	}
	
	
	
	
	

	
}


