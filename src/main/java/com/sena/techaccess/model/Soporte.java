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
	private String email;
	private String mensaje;
	private String nombre;
	private String sujeto;

	public Soporte() {

	}

	public Soporte(Integer id, String email, String mensaje, String nombre, String sujeto) {
		super();
		this.id = id;
		this.email = email;
		this.mensaje = mensaje;
		this.nombre = nombre;
		this.sujeto = sujeto;
	}

	public Integer getId() {
		return id;
	}	

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSujeto() {
		return sujeto;
	}

	public void setSujeto(String sujeto) {
		this.sujeto = sujeto;
	}

	@Override
	public String toString() {
		return "Soporte [id=" + id + ", email=" + email + ", mensaje=" + mensaje + ", nombre=" + nombre + ", sujeto="
				+ sujeto + "]";
	}

}
