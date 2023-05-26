package com.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Es el modelo de la clase para la creación de un Cliente
 * @author a026577592b
 *
 */
@Entity
@Table(name="cliente")
public class Cliente {

	/**
	 * Id del cliente
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	/**
	 * El nombre del cliente
	 */
	@Column(name="nombre")
	private String nombre;
	
	/**
	 * Apellido del cliente
	 */
	@Column(name="apellidos")
	private String apellidos;
	
	/**
	 * Edad del cliente
	 */
	@Column(name="edad")
	private int edad;
	
	/**
	 * Altura del cliente
	 */
	@Column(name="altura")
	private double altura;
	
	/**
	 * Peso del cliente
	 */
	@Column(name="peso")
	private int peso;
	
	/**
	 * Es la ruta de la imagen del cliente
	 */
	@Column(name="picPath")
	private String picPath;

	/**
	 * Es el constructor de la clase cliente
	 */
	public Cliente() {
		super();
	}
	
	/**
	 * Es el constructor de la clase cliente con los parametros 
	 * @param nombre Nombre del cliente
	 * @param apellidos Apellidos del cliente
	 * @param edad Edad del cliente
	 * @param altura Altura del cliente
	 * @param peso Peso del clienbte
	 * @param picPath Ruta de la imagen del cliente
	 */
	public Cliente(String nombre, String apellidos, int edad, double altura, int peso, String picPath) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.altura = altura;
		this.peso = peso;
		this.picPath = picPath;
	}

	/**
	 * Obtencion del id Cliente
	 * @return Devuelve el id del cliente
	 */
	public int getId() {
		return id;
	}

	/**
	 * Obtiene el nombre del cliente
	 * @return Devuelve el nombre del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene los apellidos del cliente
	 * @return Devuelve el apellido del cliente
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Obtiene la edad del cliente
	 * @return Devuelve la edad del cliente
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Obtiene la altura del cliente
	 * @return Devuelve la altura del cliente
	 */
	public double getAltura() {
		return altura;
	}

	/**
	 * Obtiene el peso del cliente
	 * @return Devuelve el peso del cliente
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * Establece el id del cliente
	 * @param id Id del cliente
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Establece el nombre del cliente
	 * @param nombre El nombre del cliente
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece los apellidos del cliente
	 * @param apellidos Apellido del cliente
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Establece la edad del cliente
	 * @param edad Edad del cliente
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Establece la altura del cliente
	 * @param altura La altura del cliente
	 */
	public void setAltura(double altura) {
		this.altura = altura;
	}

	/**
	 * Establece el peso del cliente
	 * @param peso PEso del cliente
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * Devuelve la ruta de la imagen del Cliente
	 * @return La ruta de la imagen del cliente
	 */
	public String getPicPath() {
		return picPath;
	}

	/**
	 * Establece la ruta de imagen del cliente
	 * @param picPath Ruta de la imagen del cliente
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	
	

}
