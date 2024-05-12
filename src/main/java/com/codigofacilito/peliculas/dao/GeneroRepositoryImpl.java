package com.codigofacilito.peliculas.dao;

import org.springframework.stereotype.Repository;

import com.codigofacilito.peliculas.entities.Genero;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class GeneroRepositoryImpl implements IGeneroRepository{

	//Para menejar la conexion a la BD y la operaciones Crud, utilizaremos EntityManager:	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void save(Genero genero) {
		entityManager.persist(genero);		
	}

	@Override
	@Transactional
	public Genero findByIdGenero(Long idGenero) {
		return entityManager.find(Genero.class, idGenero);
	}

}
