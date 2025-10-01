package com.sena.techaccess.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Usuario> usuario = new ArrayList<>();

	public Dispositivo() {
	}

	public Dispositivo(Integer id, String tipoD, String marca, String color, List<Usuario> usuario) {
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

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Dispositivo [id=" + idDisp + ", tipoD=" + tipoD + ", marca=" + marca + ", color=" + color + ", usuario="
				+ usuario + "]";
	}

}
