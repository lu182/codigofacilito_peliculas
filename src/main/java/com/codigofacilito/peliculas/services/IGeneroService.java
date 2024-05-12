package com.codigofacilito.peliculas.services;

import java.util.List;

import com.codigofacilito.peliculas.entities.Genero;

public interface IGeneroService {
	
	public void save(Genero genero);
	public Genero findByIdGenero(Long idGenero);
	public void delete(Long idGenero);
	public List<Genero>findAllGeneros();

}
