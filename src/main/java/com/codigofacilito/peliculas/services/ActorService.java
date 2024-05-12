package com.codigofacilito.peliculas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigofacilito.peliculas.dao.IActorRepository;
import com.codigofacilito.peliculas.entities.Actor;

@Service
public class ActorService implements IActorService {
	
	//Inyectamos repositorio:
	@Autowired
	private IActorRepository iActorRepository;
	

	//@Override
	public List<Actor> findAllActores() {		
		return (List<Actor>) iActorRepository.findAll(); //casteamos
	}	

	@Override
	public List<Actor> findAllActoresById(List<Long> ids) {
		return (List<Actor>) iActorRepository.findAllById(ids); //casteamos
	}

}
