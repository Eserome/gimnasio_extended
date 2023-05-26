package com.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase modelo para la creación del cliente
 * @author a026577592b
 *
 */
@Entity
@Table(name="ejercicio")
public class Ejercicio {

	/**
	 * Id del ejercicio
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	/**
	 * Nombre del ejercicio
	 */
	@Column(name="nombre")
	private String nombre;
	
	/**
	 * Numero de series del ejercicio
	 */
	@Column(name="numeroDeSeries")
	private int numeroDeSeries;
	
	/**
	 * Numero de repeticiones del ejercicio 
	 */
	@Column(name="repeticiones")
	private int repeticiones;
	
	/**
	 * Carga en Kg del ejercicio
	 */
	@Column(name="cargaEnKg")
	private int cargaEnKg;
	
	/**
	 * Ruta de la imagen del ejercicio
	 */
	@Column(name="picPath")
	private String picPath;
	
	/**
	 * Zona donde se encuetra el ejercicio
	 */
	@Column(name="zona")
	private String zona;
	
	/**
	 * Cosntructor del ejercicio
	 */
	public Ejercicio(){
		super();
	}

	/**
	 * Constructor de la creacion del ejercicio con parametros
	 * @param nombre Nombre del ejercicio
	 * @param numeroDeSeries Numero de series del ejercicio
	 * @param repeticiones Numero de repeticiones del ejercicio
	 * @param cargaEnKg Carga en kg del ejercicio
	 * @param picPath Ruta de la imagen del ejercicio
	 * @param zona Zona del ejercicio
	 */
	public Ejercicio(String nombre, int numeroDeSeries, int repeticiones, int cargaEnKg, String picPath, String zona) {
		super();
		this.nombre = nombre;
		this.numeroDeSeries = numeroDeSeries;
		this.repeticiones = repeticiones;
		this.cargaEnKg = cargaEnKg;
		this.picPath = picPath;
		this.zona = zona;
	}

	/**
	 * Obtiene el id del ejercicio
	 * @return Id del ejercicio
	 */
	public int getId() {
		return id;
	}

	/**
	 * Obtiene el nombre del ejercicio
	 * @return Nombre del ejercicio
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene el numero de series del ejercicio
	 * @return Numero de series del ejercicio
	 */
	public int getNumeroDeSeries() {
		return numeroDeSeries;
	}

	/**
	 * Obtiene el numero de repeticiones del ejercicio
	 * @return Devuelve las repeticiones del ejercicio
	 */
	public int getRepeticiones() {
		return repeticiones;
	}

	/**
	 * Obtiene la carga en kg del ejercicio
	 * @return Devuelve la carga en kg del ejercicio
	 */
	public int getCargaEnKg() {
		return cargaEnKg;
	}
	
	/**
	 * Obtiene la zona en la que se encuentra el ejercicio
	 * @return Devuelve la zonba en la que se encuentra el ejercicio
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * Establece el id del ejercicio
	 * @param id Id del ejercicio
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Establece el nombre del ejercicio
	 * @param nombre Nombre del ejercicio
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece el numero de series del ejercicio
	 * @param numeroDeSeries Numero de series del ejercicio
	 */
	public void setNumeroDeSeries(int numeroDeSeries) {
		this.numeroDeSeries = numeroDeSeries;
	}

	/**
	 * Establece el numero de repeticiones del ejercicio
	 * @param repeticiones Numero de repeticiones
	 */
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	/**
	 * Establece la carga en kg del ejercicio
	 * @param cargaEnKg Carga de kg en ejercicio
	 */
	public void setCargaEnKg(int cargaEnKg) {
		this.cargaEnKg = cargaEnKg;
	}
	
	/**
	 * Devuelve la ruta de la imagen del ejercicio
	 * @return Ruta de la imagen del ejercicio
	 */
	public String getPicPath() {
		return picPath;
	}

	/**
	 * Establece la ruta de la imagen del ejercicio
	 * @param picPath Ruta de la imagen del ejercicio
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	/**
	 * Establece la zona del ejercicio
	 * @param zona
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	
	
}
