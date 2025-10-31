package com.sena.techaccess.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ficha")
public class Ficha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFicha;
	private String designacion;
	private String nombrePrograma;
	private Integer numFicha;
	private String jornada;

	@OneToMany(mappedBy = "ficha")
	private List<Usuario> usuario;

	// constructor vacío
	public Ficha() {
	}

	// contructor con campos
	public Ficha(Integer idFicha, String designacion, String nombrePrograma, Integer numFicha, String jornada,
			List<Usuario> usuario) {
		super();
		this.idFicha = idFicha;
		this.designacion = designacion;
		this.nombrePrograma = nombrePrograma;
		this.numFicha = numFicha;
		this.jornada = jornada;
		this.usuario = usuario;
	}

	// getters and setters
	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public String getDesignacion() {
		return designacion;
	}

	public void setDesignacion(String designacion) {
		this.designacion = designacion;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public Integer getNumFicha() {
		return numFicha;
	}

	public void setNumFicha(Integer numFicha) {
		this.numFicha = numFicha;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	// toString
	@Override
	public String toString() {
		return "Ficha [idFicha=" + idFicha + ", designacion=" + designacion + ", nombrePrograma=" + nombrePrograma
				+ ", numFicha=" + numFicha + ", jornada=" + jornada + ", usuario=" + usuario + "]";
	}

}