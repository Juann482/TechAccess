package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "areaJobs")

public class AreaJobs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idarea;
	private String nomArea;

	// Const. Vacio
	public AreaJobs() {

	}

	// Const. con Campos

	public AreaJobs(Integer idarea, String nomArea) {
		super();
		this.idarea = idarea;
		this.nomArea = nomArea;
	}

	// Getters & Setters

	public Integer getIdarea() {
		return idarea;
	}

	public void setIdarea(Integer idarea) {
		this.idarea = idarea;
	}

	public String getNomArea() {
		return nomArea;
	}

	public void setNomArea(String nomArea) {
		this.nomArea = nomArea;
	}

	// Metod. To String
	@Override
	public String toString() {
		return "AreaJobs [idarea=" + idarea + ", nomArea=" + nomArea + "]";
	}

}