package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "dispositivo")
public class Dispositivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tipoD;
	private String marca;
	private String color;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@OneToOne(mappedBy = "dispositivo")
	private Acceso acceso;

	public Dispositivo() {
	}
	
	public Dispositivo(Integer id, String tipoD, String marca, String color, Usuario usuario) {
		super();
		this.id = id;
		this.tipoD = tipoD;
		this.marca = marca;
		this.color = color;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoD() {
		return tipoD;
	}

	public void setTipoD(String tipoD) {
		this.tipoD = tipoD;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	@Override
	public String toString() {
		return "Dispositivo [id=" + id + ", tipoD=" + tipoD + ", marca=" + marca + ", color=" + color + ", usuario="
				+ usuario + "]";
	}
}