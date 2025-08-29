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
	private String nuumFicha;
	
	@ManyToOne
	private Usuario usuario;
	
	//constructor vacío
	public Ficha() {
	}

	//contructor con campos
	public Ficha(Integer idFicha, String nombrePrograma, String nuumFicha) {
		super();
		this.idFicha = idFicha;
		this.nombrePrograma = nombrePrograma;
		this.nuumFicha = nuumFicha;
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

	public String getNuumFicha() {
		return nuumFicha;
	}

	public void setNuumFicha(String nuumFicha) {
		this.nuumFicha = nuumFicha;
	}

	//toString
	@Override
	public String toString() {
		return "Ficha [idFicha=" + idFicha + ", nombrePrograma=" + nombrePrograma + ", nuumFicha=" + nuumFicha + "]";
	}
	

}