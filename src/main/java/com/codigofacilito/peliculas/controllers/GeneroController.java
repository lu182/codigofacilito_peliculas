package com.codigofacilito.peliculas.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigofacilito.peliculas.dao.IGeneroRepository;
import com.codigofacilito.peliculas.entities.Genero;

@RestController
public class GeneroController {
	
	private IGeneroRepository iGeneroRepository;
	
	public GeneroController(IGeneroRepository iGeneroRepository ) {
		this.iGeneroRepository = iGeneroRepository;
	}
	
	@PostMapping("genero")
	public Long guardar(@RequestParam String nombre_genero) {
		Genero genero = new Genero();
		genero.setNombreGenero(nombre_genero);
		
		iGeneroRepository.save(genero);
		
		return genero.getIdGenero();
	}
	
	@GetMapping("genero/{idGen}")
	public String buscarPorId(@PathVariable(name = "idGen") Long id_genero ) {
		
		return iGeneroRepository.findByIdGenero(id_genero).getNombreGenero();
	}

}
