package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Motivo")
public class MotivoExcusas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String Enfermedad;
	private String Incapacidadmédica;
	private String DerechoVoto;
	private String CalamidadDoméstica;
	private String Otro;

	public MotivoExcusas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MotivoExcusas(Integer id, String enfermedad, String incapacidadmédica, String derechoVoto,
			String calamidadDoméstica, String otro) {
		super();
		this.id = id;
		Enfermedad = enfermedad;
		Incapacidadmédica = incapacidadmédica;
		DerechoVoto = derechoVoto;
		CalamidadDoméstica = calamidadDoméstica;
		Otro = otro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnfermedad() {
		return Enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		Enfermedad = enfermedad;
	}

	public String getIncapacidadmédica() {
		return Incapacidadmédica;
	}

	public void setIncapacidadmédica(String incapacidadmédica) {
		Incapacidadmédica = incapacidadmédica;
	}

	public String getDerechoVoto() {
		return DerechoVoto;
	}

	public void setDerechoVoto(String derechoVoto) {
		DerechoVoto = derechoVoto;
	}

	public String getCalamidadDoméstica() {
		return CalamidadDoméstica;
	}

	public void setCalamidadDoméstica(String calamidadDoméstica) {
		CalamidadDoméstica = calamidadDoméstica;
	}

	public String getOtro() {
		return Otro;
	}

	public void setOtro(String otro) {
		Otro = otro;
	}

	@Override
	public String toString() {
		return "MotivoExcusas [id=" + id + ", Enfermedad=" + Enfermedad + ", Incapacidadmédica=" + Incapacidadmédica
				+ ", DerechoVoto=" + DerechoVoto + ", CalamidadDoméstica=" + CalamidadDoméstica + ", Otro=" + Otro
				+ "]";
	}

}
