package com.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ejercicio")
public class Ejercicio {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="numeroDeSeries")
	private int numeroDeSeries;
	
	@Column(name="repeticiones")
	private int repeticiones;
	
	@Column(name="cargaEnKg")
	private int cargaEnKg;
	
	@Column(name="picPath")
	private String picPath;
	
	@Column(name="zona")
	private String zona;
	
	public Ejercicio(){
		super();
	}

	public Ejercicio(String nombre, int numeroDeSeries, int repeticiones, int cargaEnKg, String picPath, String zona) {
		super();
		this.nombre = nombre;
		this.numeroDeSeries = numeroDeSeries;
		this.repeticiones = repeticiones;
		this.cargaEnKg = cargaEnKg;
		this.picPath = picPath;
		this.zona = zona;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNumeroDeSeries() {
		return numeroDeSeries;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public int getCargaEnKg() {
		return cargaEnKg;
	}
	
	public String getZona() {
		return zona;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNumeroDeSeries(int numeroDeSeries) {
		this.numeroDeSeries = numeroDeSeries;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public void setCargaEnKg(int cargaEnKg) {
		this.cargaEnKg = cargaEnKg;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public void setZona(String zona) {
		this.zona = zona;
	}

	
	
}
