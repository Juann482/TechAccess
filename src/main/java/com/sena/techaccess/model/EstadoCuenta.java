package com.sena.techaccess.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estadocuenta")
public class EstadoCuenta {
	
	@Id
	@Column(name = "idEstado")
	private Integer idEstado;
	@Column(name = "nombreEstado", length = 45, nullable = false, unique = true)
	private String nombreEstado;
	@OneToMany(mappedBy = "estadoCuenta")
	private List<Usuario> usuarios;

	public EstadoCuenta() {
	}

	public EstadoCuenta(Integer idEstado, String nombreEstado) {
		this.idEstado = idEstado;
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "EstadoCuenta [idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + "]";
	}
}