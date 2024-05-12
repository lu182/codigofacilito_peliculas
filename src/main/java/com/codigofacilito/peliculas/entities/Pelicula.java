package com.codigofacilito.peliculas.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "peliculas")
public class Pelicula implements Serializable{

	private static final long serialVersionUID = -4623764282362001928L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idPelicula;
	
	@Column(name = "nombre")
	private String nombrePelicula;
	
	@Column(name = "fecha_estreno")
	@Temporal(TemporalType.DATE) //para que guarde solamente la fecha sin la hora
	@DateTimeFormat(pattern="yyyy-MM-dd") //definimos el formato con el que vamos a guardar la fecha en bd.
	private Date fechaEstreno;		
	
	@OneToOne //1 Pelicula va a tener 1 Género
	private Genero generoPelicula;
	
	//private List<Actor> protagonistas; 1 Pelicula tiene muchos protgonistas
	
	
	public Pelicula() {
		
	}
	
	
	public Long getIdPelicula() {
		return idPelicula;
	}
	public void setIdPelicula(Long idPelicula) {
		this.idPelicula = idPelicula;
	}
	public String getNombrePelicula() {
		return nombrePelicula;
	}
	public void setNombrePelicula(String nombrePelicula) {
		this.nombrePelicula = nombrePelicula;
	}
	public Date getFechaEstreno() {
		return fechaEstreno;
	}
	public void setFechaEstreno(Date fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}
	public Genero getGeneroPelicula() {
		return generoPelicula;
	}
	public void setGeneroPelicula(Genero generoPelicula) {
		this.generoPelicula = generoPelicula;
	}
	/*public List<Actor> getProtagonistas() {
		return protagonistas;
	}
	public void setProtagonistas(List<Actor> protagonistas) {
		this.protagonistas = protagonistas;
	}*/


}
