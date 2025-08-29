package com.sena.techaccess.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
@Table(name = "acceso")
public class Acceso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer idacceso;
	    private Date horaIngreso;
	    private Date horaEgreso;
	    private String dispositivos;


	//>estadopermanencia
//>areajobs

	
		@OneToOne
		private Usuario usuario;
		
		@ManyToOne
		private EstadoPermanencia estadoPermanencia;  //detalleacceso
		
		@ManyToOne
		private Vigilancia vigilancia;
		
		//constructor vacio
		public Acceso() {
		}
		
		//constructor con campos
		public Acceso(Integer idacceso, Date horaIngreso, Date horaEgreso, String dispositivos) {
			super();
			this.idacceso = idacceso;
			this.horaIngreso = horaIngreso;
			this.horaEgreso = horaEgreso;
			this.dispositivos = dispositivos;
		}
		
		//getters and setters
		public Integer getIdacceso() {
			return idacceso;
		}


		public void setIdacceso(Integer idacceso) {
			this.idacceso = idacceso;
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


		public String getDispositivos() {
			return dispositivos;
		}


		public void setDispositivos(String dispositivos) {
			this.dispositivos = dispositivos;
		}

		@Override
		public String toString() {
			return "Acceso [idacceso=" + idacceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso
					+ ", dispositivos=" + dispositivos + "]";
		}

		

}