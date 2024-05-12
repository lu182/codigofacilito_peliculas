package com.codigofacilito.peliculas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigofacilito.peliculas.dao.IPeliculaRepository;
import com.codigofacilito.peliculas.entities.Pelicula;

@Service
public class PeliculaService implements IPeliculaService {
	
	//Inyectamos repositorio:
	@Autowired
	private IPeliculaRepository iPeliculaRepository;
	

	@Override
	public void save(Pelicula pelicula) {
		iPeliculaRepository.save(pelicula);
		
	}

	@Override
	public Pelicula findByIdPelicula(Long idPelicula) {
		return iPeliculaRepository.findById(idPelicula).orElse(null); //como devuelve un Optional, en caso que no lo encuentre, devuelve null
	}

	@Override
	public void delete(Long idPelicula) {
		iPeliculaRepository.deleteById(idPelicula);	
		
	}

	@Override
	public List<Pelicula> findAllPeliculas() {
		return (List<Pelicula>) iPeliculaRepository.findAll(); //casteamos
	}

}
