package com.codigofacilito.peliculas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "peliculas")
public class Pelicula implements Serializable{

	private static final long serialVersionUID = -4623764282362001928L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idPelicula;
	
	@NotEmpty(message="El nombre no debe estar vacío") //que no sea nulo ni vacío
	@Column(name = "nombre")
	private String nombrePelicula;
	
	@Column(name = "fecha_estreno")
	@Temporal(TemporalType.DATE) //para que guarde solamente la fecha sin la hora
	@DateTimeFormat(pattern="yyyy-MM-dd") //definimos el formato con el que vamos a guardar la fecha en bd.
	@NotNull(message="El campo fecha de estreno no debe estar vacío")
	private Date fechaEstreno;		
	
	@NotNull()
	//@OneToOne //1 Pelicula va a tener 1 Género
	@ManyToOne
    @JoinColumn(name = "genero_pelicula_id") // nombre de la columna en la tabla peliculas que referencia al género
	private Genero generoPelicula;
	
	@ManyToMany
	private List<Actor> protagonistas; //1 Pelicula tiene muchos protagonistas/actores y a su vez esos actores pueden estar en muchas peliculas
	
	private String imagen; //campo que va a contener la imagen que subamos
	
	
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
	public List<Actor> getProtagonistas() {
		return protagonistas;
	}
	public void setProtagonistas(List<Actor> protagonistas) {
		this.protagonistas = protagonistas;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


}
