package com.sena.techaccess.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String username;
	private String email;
	private String documento;
	private String direccion;
	private String telefono;
	private String password;
	private String tipo;

	@OneToMany(mappedBy = "usuario")
	private List<Ficha> ficha = new ArrayList<>();

	@OneToMany(mappedBy = "usuario")
	private List<Permisos> permisos = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private EstadoCuenta estadoCuenta;

	@ManyToOne(fetch = FetchType.LAZY)//LAZY evita traer objetos grandes que no siempre necesita.
	private Rol rol;

	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Vigilancia vigilancia;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)//CAscadeType.ALL Borra todas sus fichas y accesos automaticamente si se llega a borrar un usuario
	private Acceso acceso;

	public Usuario() {

	}

	public Usuario(Integer id, String nombre, String username, String email, String documento, String direccion,
			String telefono, String password, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.email = email;
		this.documento = documento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.password = password;
		this.tipo = tipo;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", username=" + username + ", email=" + email
				+ ", documento=" + documento + ", direccion=" + direccion + ", telefono=" + telefono 
				 + ", tipo=" + tipo + "]";
	}

}