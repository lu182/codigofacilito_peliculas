package com.codigofacilito.peliculas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codigofacilito.peliculas.entities.Pelicula;

public interface IPeliculaService {

	public void save(Pelicula pelicula);
	public Pelicula findByIdPelicula(Long idPelicula);
	public void delete(Long idPelicula);
	public List<Pelicula>findAllPeliculas();
	public Page<Pelicula>findAllPeliculas(Pageable pageable);
}
