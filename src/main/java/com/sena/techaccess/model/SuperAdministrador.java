package com.sena.techaccess.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "superadministrador")
public class SuperAdministrador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;
	private String email;
	private String documento;
	private String telefono;
	private String password;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	private String estado;
	private String permisosEspeciales;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Usuario usuarioAsociado;

	public SuperAdministrador() {
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "ACTIVO";
	}

	public SuperAdministrador(Integer id, String nombre, String email, String documento, String telefono,
			String password, String permisosEspeciales) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.documento = documento;
		this.telefono = telefono;
		this.password = password;
		this.permisosEspeciales = permisosEspeciales;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPermisosEspeciales() {
		return permisosEspeciales;
	}

	public void setPermisosEspeciales(String permisosEspeciales) {
		this.permisosEspeciales = permisosEspeciales;
	}

	public Usuario getUsuarioAsociado() {
		return usuarioAsociado;
	}

	public void setUsuarioAsociado(Usuario usuarioAsociado) {
		this.usuarioAsociado = usuarioAsociado;
	}

	@Override
	public String toString() {
		return "SuperAdministrador [id=" + id + ", nombre=" + nombre + ", email=" + email + ", documento=" + documento
				+ ", telefono=" + telefono + ", estado=" + estado + ", permisosEspeciales=" + permisosEspeciales + "]";
	}
}