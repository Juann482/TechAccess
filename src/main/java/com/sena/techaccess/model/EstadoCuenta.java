package com.sena.techaccess.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estadoCuenta")
public class EstadoCuenta {
	public static final Integer U_Activo = 1;
	public static final Integer U_Inactivo = 2;
	public static final String NomActivo = "Activo";
	public static final String NomInactivo = "Inactivo";
	@Id
	@Column(name = "idEstado")
	private Integer idestado;
	@Column(name = "nombreEstado", length = 45, nullable = false, unique = true)
	private String nombreEstado;
	@OneToMany(mappedBy = "estadoCuenta")
	private List<Usuario> usuarios;

	public EstadoCuenta() {
	}

	public EstadoCuenta(Integer idestado, String nombreEstado) {
		this.idestado = idestado;
		this.nombreEstado = nombreEstado;
	}

	public Integer getIdestado() {
		return idestado;
	}

	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
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
		return "EstadoCuenta [idestado=" + idestado + ", nombreEstado=" + nombreEstado + "]";
	}
}