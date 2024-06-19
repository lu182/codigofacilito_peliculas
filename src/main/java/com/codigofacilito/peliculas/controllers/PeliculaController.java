package com.codigofacilito.peliculas.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codigofacilito.peliculas.entities.Actor;
import com.codigofacilito.peliculas.entities.Pelicula;
import com.codigofacilito.peliculas.services.IActorService;
import com.codigofacilito.peliculas.services.IGeneroService;
import com.codigofacilito.peliculas.services.IPeliculaService;

import jakarta.validation.Valid;

@Controller
public class PeliculaController {
	
private IPeliculaService iPeliculaService;
private IGeneroService iGeneroService;
private IActorService iActorService;
	
	public PeliculaController(IPeliculaService iPeliculaService, IGeneroService iGeneroService, 
			IActorService iActorService) { //inyectamos los servicios en el constructor
		
		this.iPeliculaService = iPeliculaService;
		this.iGeneroService = iGeneroService;
		this.iActorService = iActorService;
	}
	
	
	@GetMapping("/pelicula") //http://localhost:8080/pelicula
	public String crear(Model model) { //Le pasamos el objeto Model p/pasar los datos del controlador a la vista 
		Pelicula pelicula = new Pelicula(); //creamos instancia de Pelicula
		model.addAttribute("pelicula", pelicula); //y se lo pasamos al Model
		
		model.addAttribute("titulo", "Nueva Pelicula"); //En pelicula.html -> <title th:text="${titulo}"></title> 
		
		model.addAttribute("generos", iGeneroService.findAllGeneros()); //Traemos los generos del service que inyectamos p/mostrarse en select del form(combo box)-> th:each="genero : ${generos}"
		
		model.addAttribute("actores", iActorService.findAllActores()); //Traemos los actores del service que inyectamos p/mostrarse en
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates->pelicula.html
	}
	
	
	@GetMapping("/pelicula/{idPelicula}")
	public String editar(@PathVariable(name = "idPelicula") Long id_pelicula, Model model) {
		Pelicula pelicula = new Pelicula();
		model.addAttribute("pelicula", pelicula);
		
		model.addAttribute("titulo", "Editar Pelicula");
		
		model.addAttribute("generos", iGeneroService.findAllGeneros()); //Traemos los generos del service que inyectamos p/mostrarse en select del form(combo box)-> th:each="genero : ${generos}"
		
		model.addAttribute("actores", iActorService.findAllActores()); //Traemos los actores del service que inyectamos p/mostrarse en
		
		return "pelicula"; //nombre de la vista que va a retornar este método-> templates-> pelicula.html
	}
	
	@PostMapping("/pelicula") //botón guardar del form
	public String guardar(@Valid Pelicula pelicula, BindingResult br , @ModelAttribute(name = "ids") String ids, Model model) {
		
		//Al momento de guardar preguntamos si el formulario tuvo algún error. Si hubo, enviamos nuevamente el form
		if(br.hasErrors()) {
			model.addAttribute("generos", iGeneroService.findAllGeneros()); 			
			model.addAttribute("actores", iActorService.findAllActores());
			return "pelicula"; //va a retornar la vista -> pelicula.html
		}
		
		//creamos lista de tipo Long de idsProtagonistas separados por comas
		if (!ids.isEmpty()) {
			List<Long> idsProtagonistas = 
					Arrays.stream(ids.split(",")) //de esa lista de ids de tipo String, separame los ids por comas
					.map(Long::parseLong) //a ese resultado lo mapeamos utilizando la clase Long y lo parseamos a Long
					.collect(Collectors.toList()); //por último, lo convertimos a lista.
			//Le pasamos esa lista al servicio. Esto es p/saber todos los ids de actores que tendrá esa pelicula
			List<Actor> protagonistas = iActorService.findAllActoresById(idsProtagonistas);
			pelicula.setProtagonistas(protagonistas);
		}		
		
		iPeliculaService.save(pelicula); //Guardamos la pelicula en la BD		
		
		return "redirect:home"; //cuando con el form realicemos el save, vamos a redirigirlo al home.html
	}
	
	@GetMapping({"/", "/home", "/index"}) //Estas 3 opciones nos van a mandar a nuestro home.html donde estará el Catálogo
	public String home(Model model) {
		
		//Añadimos/traemos las peliculas
		model.addAttribute("peliculas", iPeliculaService.findAllPeliculas());
		
		//header.html -> <div th:if="${msj != null}" th:text="${msj}" th:class="${tipoMsj != null ? 'alert alert-' + tipoMsj : 'alert'}">
		//Al presionar Catálogo en navbar:
		model.addAttribute("msj", "Catálogo actualizado a 2024");
		model.addAttribute("tipoMsj", "success"); //success -> clase bootstrap
		
		return "home"; //Vista que devolverá -> templates-> home.html
	}	

}
