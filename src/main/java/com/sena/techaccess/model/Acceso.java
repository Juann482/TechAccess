package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Table(name = "acceso")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idacceso;

    private LocalDateTime horaIngreso;
    private LocalDateTime horaEgreso;

    @OneToOne
    @JoinColumn(name = "idDisp") 
    private Dispositivo dispositivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id") 
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestadoPermanencia") 
    private EstadoPermanencia estadoPermanencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVigilancia") 
    private Vigilancia vigilancia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idarea") 
    private AreaJobs areaJobs;

    public Acceso() {}

    public Acceso(Integer idacceso, LocalDateTime horaIngreso, LocalDateTime horaEgreso,
                  Dispositivo dispositivo, Usuario usuario,
                  EstadoPermanencia estadoPermanencia, Vigilancia vigilancia, AreaJobs areaJobs) {
        this.idacceso = idacceso;
        this.horaIngreso = horaIngreso;
        this.horaEgreso = horaEgreso;
        this.dispositivo = dispositivo;
        this.usuario = usuario;
        this.estadoPermanencia = estadoPermanencia;
        this.vigilancia = vigilancia;
        this.areaJobs = areaJobs;
    }

    public Integer getIdacceso() { return idacceso; }
    public void setIdacceso(Integer idacceso) { this.idacceso = idacceso; }

    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalDateTime horaIngreso) { this.horaIngreso = horaIngreso; }

    public LocalDateTime getHoraEgreso() { return horaEgreso; }
    public void setHoraEgreso(LocalDateTime horaEgreso) { this.horaEgreso = horaEgreso; }

    public Dispositivo getDispositivo() { return dispositivo; }
    public void setDispositivo(Dispositivo dispositivo) { this.dispositivo = dispositivo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public EstadoPermanencia getEstadoPermanencia() { return estadoPermanencia; }
    public void setEstadoPermanencia(EstadoPermanencia estadoPermanencia) { this.estadoPermanencia = estadoPermanencia; }

    public Vigilancia getVigilancia() { return vigilancia; }
    public void setVigilancia(Vigilancia vigilancia) { this.vigilancia = vigilancia; }

    public AreaJobs getAreaJobs() { return areaJobs; }
    public void setAreaJobs(AreaJobs areaJobs) { this.areaJobs = areaJobs; }

	@Override
	public String toString() {
		return "Acceso [idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
				+ ", dispositivo=" + dispositivo + ", usuario=" + usuario + ", estadoPermanencia=" + estadoPermanencia
				+ ", vigilancia=" + vigilancia + ", areaJobs=" + areaJobs + "]";
	}

   
}
