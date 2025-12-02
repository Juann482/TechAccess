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
	private String rol;
	private String estadoCuenta;
	
	//Limitado para administradores por el momento
	private String imagen;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ficha_id", nullable = true)
	private Ficha ficha;

	@OneToMany(mappedBy = "usuario")
	private List<DispositivoVisit> dispositivoVisit = new ArrayList<>();

	@OneToMany(mappedBy = "usuario")
	private List<Permisos> permisos = new ArrayList<>();

	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Vigilancia vigilancia;


	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // CAscadeType.ALL Borra todas sus fichas y accesos automaticamente si se llega a borrar un usuario																
	private Acceso acceso;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Excusas> excusas = new ArrayList<>();

	@OneToMany
	private List<Dispositivo> dispositivo = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(Integer id, String nombre, String email, String documento, String direccion, String telefono,
			String password, String rol, String estadoCuenta, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.documento = documento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.password = password;
		this.rol = rol;
		this.estadoCuenta = estadoCuenta;
		this.imagen = imagen;
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
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	public List<Permisos> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Permisos> permisos) {
		this.permisos = permisos;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public List<DispositivoVisit> getDispositivoVisit() {
		return dispositivoVisit;
	}

	public void setDispositivoVisit(List<DispositivoVisit> dispositivoVisit) {
		this.dispositivoVisit = dispositivoVisit;
	}

	public Vigilancia getVigilancia() {
		return vigilancia;
	}

	public void setVigilancia(Vigilancia vigilancia) {
		this.vigilancia = vigilancia;
	}

	public Acceso getAcceso() {
		return acceso;
	}

	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}

	public List<Excusas> getExcusas() {
		return excusas;
	}

	public void setExcusas(List<Excusas> excusas) {
		this.excusas = excusas;
	}

	public List<Dispositivo> getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(List<Dispositivo> dispositivo) {
		this.dispositivo = dispositivo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", documento=" + documento
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", password=" + password + ", rol=" + rol
				+ ", estadoCuenta=" + estadoCuenta + ", imagen=" + imagen + ", ficha=" + ficha + ", dispositivoVisit="
				+ dispositivoVisit + ", permisos=" + permisos + ", vigilancia=" + vigilancia + ", acceso=" + acceso
				+ ", excusas=" + excusas + ", dispositivo=" + dispositivo + "]";
	}

}