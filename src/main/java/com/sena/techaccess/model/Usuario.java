package com.sena.techaccess.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String email;
	private String documento;
	private String direccion;
	private String telefono;
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ficha_id", nullable = true)
	private Ficha ficha;

	@OneToMany(mappedBy = "usuario")
	private List<Permisos> permisos = new ArrayList<>();

	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "estado_cuenta")
	private EstadoCuenta estadoCuenta;

	@ManyToOne // (fetch = FetchType.LAZY)//LAZY evita traer objetos grandes que no siempre
				// necesitas.
	@JoinColumn(name = "Rol")
	private Rol rol;

	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Vigilancia vigilancia;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // CAscadeType.ALL Borra todas sus fichas y accesos
																	// automaticamente si se llega a borrar un usuario
	@JoinColumn(name = "Acceso")
	private Acceso acceso;
	
	@ManyToMany
	private List<Dispositivo> dispositivo = new ArrayList<>();

	public Usuario() {

	}

	public Usuario(Integer id, String nombre, Integer nFicha, String email, String documento, String direccion,
			String telefono, String password) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.documento = documento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.password = password;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", documento=" + documento

				+ ", direccion=" + direccion + ", telefono=" + telefono + ", password=" + password + ", ficha=" + ficha
				+ ", permisos=" + permisos + ", estadoCuenta=" + estadoCuenta + ", rol=" + rol + ", vigilancia="
				+ vigilancia + ", acceso=" + acceso + "]";
	}

}