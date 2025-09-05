package com.sena.techaccess.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estadoPermanencia")
public class EstadoPermanencia {
	
	public static final Integer P_ID_PRESENTE = 1;
	public static final Integer P_ID_AUSENTE = 2;
	public static final Integer P_ID_POR_DEFINIR = 3;
	
	public static final String P_PRESENTE = "Presente";
	public static final String P_AUSENTE = "Ausente";
	public static final String P_POR_DEFINIR = "Por definir";
	
	@Id
	private Integer idestadoPermanencia;
	
	@Column(nullable = false, length = 45, unique = true)
	private String tipoPermanencia;
	
	@OneToMany(mappedBy = "estadoPermanencia")
	private List<Vigilancia> vigilancia;
	
	@OneToMany(mappedBy = "estadoPermanencia")
	private List<Acceso> acceso;

	public EstadoPermanencia() {
	}

	public EstadoPermanencia(Integer idestadoPermanencia, String tipoPermanencia, Vigilancia vigilancia) {
		super();
		this.idestadoPermanencia = idestadoPermanencia;
		this.tipoPermanencia = tipoPermanencia;
	}

	public Integer getIdestadoPermanencia() {
		return idestadoPermanencia;
	}

	public void setIdestadoPermanencia(Integer idestadoPermanencia) {
		this.idestadoPermanencia = idestadoPermanencia;
	}

	public String getTipoPermanencia() {
		return tipoPermanencia;
	}

	public void setTipoPermanencia(String tipoPermanencia) {
		this.tipoPermanencia = tipoPermanencia;
	}

	public List<Vigilancia> getVigilancia() {
		return vigilancia;
	}

	public void setVigilancia(List<Vigilancia> vigilancia) {
		this.vigilancia = vigilancia;
	}

	public List<Acceso> getAcceso() {
		return acceso;
	}

	public void setAcceso(List<Acceso> acceso) {
		this.acceso = acceso;
	}

	@Override
	public String toString() {
		return "EstadoPermanencia [idestadoPermanencia=" + idestadoPermanencia + ", tipoPermanencia=" + tipoPermanencia
				+ ", vigilancia=" + vigilancia + ", acceso=" + acceso + "]";
	}
}