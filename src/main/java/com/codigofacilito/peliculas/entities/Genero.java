package com.codigofacilito.peliculas.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "generos")
public class Genero implements Serializable{

	private static final long serialVersionUID = -7961042232744933431L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGenero;
	
	private String nombreGenero;
	
	

	public Genero() {
		
	}

	public Long getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Long idGenero) {
		this.idGenero = idGenero;
	}

	public String getNombreGenero() {
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

}
