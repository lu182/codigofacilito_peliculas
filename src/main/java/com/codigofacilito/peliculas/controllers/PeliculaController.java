package com.codigofacilito.peliculas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codigofacilito.peliculas.entities.Pelicula;
import com.codigofacilito.peliculas.services.IPeliculaService;

@Controller
public class PeliculaController {
	
private IPeliculaService iPeliculaService;
	
	public PeliculaController(IPeliculaService iPeliculaService) { //inyectamos el servicio en el constructor
		this.iPeliculaService = iPeliculaService;
	}
	
	
	@GetMapping("/pelicula") //http://localhost:8080/pelicula
	public String crear(Model model) { //Le pasamos el objeto Model p/pasar los datos del controlador a la vista 
		Pelicula pelicula = new Pelicula(); //creamos instancia de Pelicula
		model.addAttribute("pelicula", pelicula); //y se lo pasamos al Model
		
		model.addAttribute("titulo", "Nueva Pelicula"); //En pelicula.html -> <title th:text="${titulo}"></title> 
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates->pelicula.html
	}
	
	
	@GetMapping("/pelicula/{idPelicula}")
	public String editar(@PathVariable(name = "idPelicula") Long id_pelicula, Model model) {
		Pelicula pelicula = new Pelicula();
		model.addAttribute("pelicula", pelicula);
		
		model.addAttribute("titulo", "Editar Pelicula");
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates-> pelicula.html
	}

}
