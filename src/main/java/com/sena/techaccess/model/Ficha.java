package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fichas")	
public class Ficha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFicha;
	private String nombrePrograma;
	private String numFicha;
	
	@ManyToOne
	private Usuario usuario;
	
	//constructor vacío
	public Ficha() {
	}

	//contructor con campos
	public Ficha(Integer idFicha, String nombrePrograma, String numFicha, Usuario usuario) {
		super();
		this.idFicha = idFicha;
		this.nombrePrograma = nombrePrograma;
		this.numFicha = numFicha;
		this.usuario = usuario;
	}

	//getters and setters
	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getNumFicha() {
		return numFicha;
	}

	public void setNumFicha(String numFicha) {
		this.numFicha = numFicha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//toString
	@Override
	public String toString() {
		return "Ficha [idFicha=" + idFicha + ", nombrePrograma=" + nombrePrograma + ", numFicha=" + numFicha
				+ ", usuario=" + usuario + "]";
	}

	
	

	


	
	
	

}