package com.codigofacilito.peliculas.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigofacilito.peliculas.entities.Genero;
import com.codigofacilito.peliculas.services.IGeneroService;

@RestController
public class GeneroController {
	
	private IGeneroService iGeneroService;
	
	public GeneroController(IGeneroService iGeneroService) { //inyectamos
		this.iGeneroService = iGeneroService;
	}
	
	@PostMapping("genero") //http://localhost:8080/genero --> Body->form-data->key: nombre_genero value: Comedia
	public Long guardar(@RequestParam String nombre_genero) {
		Genero genero = new Genero();
		genero.setNombreGenero(nombre_genero);
		
		iGeneroService.save(genero);
		
		return genero.getIdGenero();
	}
	
	@GetMapping("genero/{idGen}") //http://localhost:8080/genero/1
	public String buscarPorId(@PathVariable(name = "idGen") Long id_genero ) {
		
		return iGeneroService.findByIdGenero(id_genero).getNombreGenero(); //solo mostramos el nombre
	}

}
