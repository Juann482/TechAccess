package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "permisos")
public class Permisos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tipoPermiso;
	private String rutaPermiso;
	
	@ManyToOne
	private Usuario usuario;
	
	//constructor vacio
	public Permisos() {
	}

	//constructor con campos
	public Permisos(Integer id, String tipoPermiso, String rutaPermiso, Usuario usuario) {
		super();
		this.id = id;
		this.tipoPermiso = tipoPermiso;
		this.rutaPermiso = rutaPermiso;
		this.usuario = usuario;
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}

	public String getRutaPermiso() {
		return rutaPermiso;
	}

	public void setRutaPermiso(String rutaPermiso) {
		this.rutaPermiso = rutaPermiso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//toString
	@Override
	public String toString() {
		return "Permisos [id=" + id + ", tipoPermiso=" + tipoPermiso + ", rutaPermiso=" + rutaPermiso + ", usuario="
				+ usuario + "]";
	}


}
