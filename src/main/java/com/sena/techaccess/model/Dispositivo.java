package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispositivo")
public class Dispositivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDisp;
	private String tipoD;
	private String marca;
	private String color;

	
	@ManyToOne
	private Usuario usuario;
	
	@OneToOne
	private Acceso acceso;


	public Dispositivo() {
	}

	
	
	public Dispositivo(Integer id, String tipoD, String marca, String color, Usuario usuario) {

		super();
		this.idDisp = id;
		this.tipoD = tipoD;
		this.marca = marca;
		this.color = color;
		this.usuario = usuario;
	}

	public Integer getId() {
		return idDisp;
	}

	public void setId(Integer id) {
		this.idDisp = id;
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

	@Override
	public String toString() {
		return "Dispositivo [id=" + idDisp + ", tipoD=" + tipoD + ", marca=" + marca + ", color=" + color + ", usuario="
				+ usuario + "]";
	}

}
