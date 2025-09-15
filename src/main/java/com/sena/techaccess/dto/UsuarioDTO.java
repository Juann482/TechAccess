package com.sena.techaccess.dto;

public class UsuarioDTO {
	
	private String nombre;
	private String documento;
	private String email;
	private String telefono;
	private String rol;
	private String nFicha;
	private String estadoCuenta;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(String nombre, String documento, String email, String telefono, String rol, String nFicha,
			String estadoCuenta) {
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.email = email;
		this.telefono = telefono;
		this.rol = rol;
		this.nFicha = nFicha;
		this.estadoCuenta = estadoCuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getdocumento() {
		return documento;
	}

	public void setdocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getnFicha() {
		return nFicha;
	}

	public void setnFicha(String nFicha) {
		this.nFicha = nFicha;
	}

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}
	

}
