package com.codigofacilito.peliculas.services;

import java.util.List;

import com.codigofacilito.peliculas.entities.Actor;

public interface IActorService {

	public List<Actor>findAllActores();
	public List<Actor>findAllActoresById(List<Long> ids); //Dado ciertos ids de Actores, nos va a devolver ese listado de Actores
	
}
