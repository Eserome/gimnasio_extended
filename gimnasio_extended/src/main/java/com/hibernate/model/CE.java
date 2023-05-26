package com.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * Clase del modelo rutina Clientes Ejercicios
 * @author a026577592b
 *
 */
@Entity
@Table(name="cliente_has_ejercicio")
public class CE {

	/**
	 * Id del cliente
	 */
	@Id
	@Column(name="cliente_id")
	private int cliente_id;
	
	/**
	 * Id del ejercicio
	 */
	@Id
	@Column(name="ejercicio_id")
	private int ejercicio_id;
	
	/**
	 * Constructor de la relacion Clientes Ejercicios
	 */
	public CE() {
		super();
	}
	
	/**
	 * Constructor de la relación Cliente Ejercicio con el paso de parametros correspondientes
	 * @param cliente_id El id del Cliente
	 * @param ejercicio_id El id del Ejercicio
	 */
	public CE(int cliente_id, int ejercicio_id) {
		super();
		this.cliente_id = cliente_id;
		this.ejercicio_id = ejercicio_id;
	}

	/**
	 * Obtención del id cliente
	 * @return Devuelve el Id del cliente
	 */
	public int getCliente_id() {
		return cliente_id;
	}

	/**
	 * Obtencion del id ejercicio
	 * @return Devuelve el id del ejercicio
 	 */
	public int getEjercicio_id() {
		return ejercicio_id;
	}
	
	/**
	 * Introduce el id cliente deseado
	 * @param cliente_id El id cliente que deseas establecer
	 */
	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	/**
	 * Introduce el id ejercicio deseado
	 * @param ejercicio_id El id del ejercicio que desas establecer
	 */
	public void setEjercicio_id(int ejercicio_id) {
		this.ejercicio_id = ejercicio_id;
	}


	
	
	
	

}
