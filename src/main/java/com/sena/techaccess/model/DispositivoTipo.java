package com.sena.techaccess.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiposDis")
public class DispositivoTipo {

	@Id
	private String Marca ;
}
