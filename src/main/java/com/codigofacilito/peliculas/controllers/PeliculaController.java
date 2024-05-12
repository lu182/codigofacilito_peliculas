package com.codigofacilito.peliculas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codigofacilito.peliculas.entities.Pelicula;
import com.codigofacilito.peliculas.services.IGeneroService;
import com.codigofacilito.peliculas.services.IPeliculaService;

@Controller
public class PeliculaController {
	
private IPeliculaService iPeliculaService;
private IGeneroService iGeneroService;
	
	public PeliculaController(IPeliculaService iPeliculaService, IGeneroService iGeneroService) { //inyectamos los servicios en el constructor
		this.iPeliculaService = iPeliculaService;
		this.iGeneroService = iGeneroService;
	}
	
	
	@GetMapping("/pelicula") //http://localhost:8080/pelicula
	public String crear(Model model) { //Le pasamos el objeto Model p/pasar los datos del controlador a la vista 
		Pelicula pelicula = new Pelicula(); //creamos instancia de Pelicula
		model.addAttribute("pelicula", pelicula); //y se lo pasamos al Model
		
		model.addAttribute("titulo", "Nueva Pelicula"); //En pelicula.html -> <title th:text="${titulo}"></title> 
		
		model.addAttribute("generos", iGeneroService.findAllGeneros()); //Traemos los generos del service que inyectamos p/mostrarse en select del form(combo box)-> th:each="genero : ${generos}"
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates->pelicula.html
	}
	
	
	@GetMapping("/pelicula/{idPelicula}")
	public String editar(@PathVariable(name = "idPelicula") Long id_pelicula, Model model) {
		Pelicula pelicula = new Pelicula();
		model.addAttribute("pelicula", pelicula);
		
		model.addAttribute("titulo", "Editar Pelicula");
		
		model.addAttribute("generos", iGeneroService.findAllGeneros()); //Traemos los generos del service que inyectamos p/mostrarse en select del form(combo box)-> th:each="genero : ${generos}"
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates-> pelicula.html
	}
	
	@PostMapping("/pelicula") //botón guardar del form
	public String guardar(Pelicula pelicula) {
		iPeliculaService.save(pelicula); //Guardamos la pelicula en la BD
		return "redirect:home"; //cuando con el form realicemos el save, vamos a redirigirlo al home.html
	}
	
	@GetMapping({"/", "/home", "/index"}) //Estas 3 opciones nos van a mandar a nuestro home.html
	public String home() {
		return "home"; //nombre de la vista que va a retornar este método-> templates-> home.html
	}

}
