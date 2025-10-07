
package com.sena.techaccess.model;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Excusas")
public class Excusas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String motivo;
	private String fecha;
	private String soporte;

	@Transient
	private transient MultipartFile soporteFile;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Ficha ficha;

	public Excusas() {

	}

	public Excusas(Integer id, String nombres, Integer ndocumento, String motivo, String fecha, String soporte,
			MultipartFile soporteFile, Usuario usuario, Ficha ficha) {
		super();
		this.id = id;

		this.motivo = motivo;
		this.fecha = fecha;
		this.soporte = soporte;
		this.soporteFile = soporteFile;
		this.usuario = usuario;
		this.ficha = ficha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Ficha getFicha() {
		return ficha;
	}

	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	@Override
	public String toString() {
		return "Excusas [id=" + id + ", nombres=" + ", motivo=" + motivo + ", fecha=" + fecha + ", soporte=" + soporte
				+ ", usuario=" + usuario + ", ficha=" + ficha + "]";
	}

	public MultipartFile getSoporteFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
