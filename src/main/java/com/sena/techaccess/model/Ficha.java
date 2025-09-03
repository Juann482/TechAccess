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
	public Ficha(Integer idFicha, String nombrePrograma, String numFicha) {
		super();
		this.idFicha = idFicha;
		this.nombrePrograma = nombrePrograma;
		this.numFicha = numFicha;
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

	public String getnumFicha() {
		return numFicha;
	}

	public void setnumFicha(String numFicha) {
		this.numFicha = numFicha;
	}

	//toString
	@Override
	public String toString() {
		return "Ficha [idFicha=" + idFicha + ", nombrePrograma=" + nombrePrograma + ", numFicha=" + numFicha + "]";
	}
	

}