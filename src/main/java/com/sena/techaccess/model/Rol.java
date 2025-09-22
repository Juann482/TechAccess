package com.sena.techaccess.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "tipo", length = 45, nullable = false, unique = true)
	private String tipo;

	@OneToMany(mappedBy = "rol")
	private List<Usuario> usuarios;

	public Rol() {
	}

	public Rol(Integer id, String tipo) {
		this.id = id;
		this.tipo = tipo;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuarios = usuario;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", tipo=" + tipo + "]";
	}
}
