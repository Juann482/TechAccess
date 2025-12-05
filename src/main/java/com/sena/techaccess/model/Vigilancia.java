package com.sena.techaccess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vigilancia")
public class Vigilancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVigilancia;

	@Temporal(TemporalType.TIMESTAMP)
	private Date horaIngreso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date horaEgreso;

	private String turno;

	@ManyToOne(fetch = FetchType.LAZY)
	private EstadoPermanencia estadoPermanencia;

	// ❌ Antes: cascade = CascadeType.ALL (peligroso)
	// ✔ Corregido: sin cascade para no alterar al usuario
	@OneToOne(fetch = FetchType.LAZY)
	
	private Usuario usuario;

	// ✔ Accesos sí deben mantenerse en cascada
	@OneToMany(mappedBy = "vigilancia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Acceso> accesos = new ArrayList<>();

	public Vigilancia() {
	}

	public Vigilancia(Integer idVigilancia, Date horaIngreso, Date horaEgreso, String turno) {
		this.idVigilancia = idVigilancia;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.turno = turno;
	}

	public Integer getIdVigilancia() {
		return idVigilancia;
	}

	public void setIdVigilancia(Integer idVigilancia) {
		this.idVigilancia = idVigilancia;
	}

	public Date getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(Date horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public Date getHoraEgreso() {
		return horaEgreso;
	}

	public void setHoraEgreso(Date horaEgreso) {
		this.horaEgreso = horaEgreso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public EstadoPermanencia getEstadoPermanencia() {
		return estadoPermanencia;
	}

	public void setEstadoPermanencia(EstadoPermanencia estadoPermanencia) {
		this.estadoPermanencia = estadoPermanencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Acceso> getAccesos() {
		return accesos;
	}

	public void setAccesos(List<Acceso> accesos) {
		this.accesos = accesos;
	}

	// ✔ Método auxiliar para añadir acceso sin errores
	public void addAcceso(Acceso acceso) {
		accesos.add(acceso);
		acceso.setVigilancia(this);
	}

	@Override
	public String toString() {
		return "Vigilancia [idVigilancia=" + idVigilancia + ", horaIngreso=" + horaIngreso + ", horaEgreso="
				+ horaEgreso + ", turno=" + turno + ", estadoPermanencia="
				+ (estadoPermanencia != null ? estadoPermanencia.getTipoPermanencia() : null) + ", usuario="
				+ (usuario != null ? usuario.getId() : null) + ", accesos=" + (accesos != null ? accesos.size() : 0)
				+ "]";
	}

}
