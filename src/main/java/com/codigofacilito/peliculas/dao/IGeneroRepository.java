package com.codigofacilito.peliculas.dao;

import org.springframework.data.repository.CrudRepository;

import com.codigofacilito.peliculas.entities.Genero;

public interface IGeneroRepository extends CrudRepository<Genero,Long>{
	
	//Borramos estos métodos porque ya los tiene CurdRepository:
	/*public void save(Genero genero);
	public Genero findByIdGenero(Long idGenero);*/
}
