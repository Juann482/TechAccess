
package com.sena.techaccess.model;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Excusas")
public class Excusas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombres;
	private Integer ndocumento;
	private String ficha;
	private Integer numeroFicha;
	private String motivo;
	private String fecha;
	private String soporte;
	
	@Transient
	private transient MultipartFile soporteFile;


	public Excusas() {

	}

	public Excusas(Integer id, String nombres, Integer ndocumento, String ficha, Integer numeroFicha, String motivo,
			String fecha, String soporte) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.ndocumento = ndocumento;
		this.ficha = ficha;
		this.numeroFicha = numeroFicha;
		this.motivo = motivo;
		this.fecha = fecha;
		this.soporte = soporte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Integer getNdocumento() {
		return ndocumento;
	}

	public void setNdocumento(Integer ndocumento) {
		this.ndocumento = ndocumento;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public Integer getNumeroFicha() {
		return numeroFicha;
	}

	public void setNumeroFicha(Integer numeroFicha) {
		this.numeroFicha = numeroFicha;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	@Override
	public String toString() {
		return "Excusas [id=" + id + ", nombres=" + nombres + ", Ndocumento=" + ndocumento + ", ficha=" + ficha
				+ ", numeroFicha=" + numeroFicha + ", motivo=" + motivo + ", fecha=" + fecha + ", soporte=" + soporte
				+ "]";
	}

	
	

	public MultipartFile getSoporteFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
