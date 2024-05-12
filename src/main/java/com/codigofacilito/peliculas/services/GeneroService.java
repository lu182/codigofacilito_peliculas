package com.codigofacilito.peliculas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigofacilito.peliculas.dao.IGeneroRepository;
import com.codigofacilito.peliculas.entities.Genero;

@Service
public class GeneroService implements IGeneroService{
	
	//Inyectamos repositorio:
	@Autowired
	private IGeneroRepository iGeneroRepository;

	@Override
	public void save(Genero genero) {		
		iGeneroRepository.save(genero);
	}

	@Override
	public Genero findByIdGenero(Long idGenero) {
		return iGeneroRepository.findById(idGenero).orElse(null); //en caso que no lo encuentre, devuelve null
	}

	@Override
	public void delete(Long idGenero) {
		iGeneroRepository.deleteById(idGenero);		
	}

	@Override
	public List<Genero> findAllGeneros() {		
		return (List<Genero>) iGeneroRepository.findAll(); //casteamos
	}

}
