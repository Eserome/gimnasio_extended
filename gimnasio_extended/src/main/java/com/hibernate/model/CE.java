package com.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente_has_ejercicio")
public class CE {

	@Id
	@Column(name="cliente_id")
	private int cliente_id;
	
	@Id
	@Column(name="ejercicio_id")
	private int ejercicio_id;
	
	public CE() {
		super();
	}
	
	public CE(int cliente_id, int ejercicio_id) {
		super();
		this.cliente_id = cliente_id;
		this.ejercicio_id = ejercicio_id;
	}

	public int getCliente_id() {
		return cliente_id;
	}

	public int getEjercicio_id() {
		return ejercicio_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public void setEjercicio_id(int ejercicio_id) {
		this.ejercicio_id = ejercicio_id;
	}


	
	
	
	

}
