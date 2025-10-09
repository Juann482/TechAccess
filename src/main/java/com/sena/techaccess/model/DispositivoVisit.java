package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispoVisit")
public class DispositivoVisit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tipo;
	private String marca;
	private String color;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Usuario usuario;

	public DispositivoVisit() {}

	public DispositivoVisit(Integer id, String tipo, String marca, String color, Usuario usuario) {
		super();
		this.id = id;
		this.tipo = tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		return "DispositivoVIsit [id=" + id + ", tipo=" + tipo + ", marca=" + marca + ", color=" + color + ", usuario="
				+ usuario + "]";
	}
		
}
