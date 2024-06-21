package com.codigofacilito.peliculas.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codigofacilito.peliculas.entities.Actor;
import com.codigofacilito.peliculas.entities.Pelicula;
import com.codigofacilito.peliculas.services.IActorService;
import com.codigofacilito.peliculas.services.IArchivoService;
import com.codigofacilito.peliculas.services.IGeneroService;
import com.codigofacilito.peliculas.services.IPeliculaService;

import jakarta.validation.Valid;

@Controller
public class PeliculaController {
	
private IPeliculaService iPeliculaService;
private IGeneroService iGeneroService;
private IActorService iActorService;
private IArchivoService iArchivoService;
	
	public PeliculaController(IPeliculaService iPeliculaService, IGeneroService iGeneroService, 
			IActorService iActorService, IArchivoService iArchivoService) { //inyectamos los servicios en el constructor
		
		this.iPeliculaService = iPeliculaService;
		this.iGeneroService = iGeneroService;
		this.iActorService = iActorService;
		this.iArchivoService = iArchivoService;
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
	
	
	@GetMapping("/pelicula/{id}") 
	public String editar(@PathVariable(name = "id") Long id, Model model) { //En pelicula.html -> <input id="id" name="id" type="text" th:field="*{idPelicula}">
		
		Pelicula pelicula = iPeliculaService.findByIdPelicula(id);
		
		/*String ids = "";
		for (Actor actor : pelicula.getProtagonistas()) {
			if("".equals(ids)) {
				ids = actor.getIdActor().toString();
			}else {
				ids = ids + "." + actor.getIdActor().toString();
			}
		}*/
		
		/*String ids = pelicula.getProtagonistas().stream()
	                    .map(actor -> actor.getIdActor().toString())
	                    .collect(Collectors.joining(","));*/
	    
	    //Obtener IDs de los actores asociados a la película
		String ids = "";
	    if (!pelicula.getProtagonistas().isEmpty()) {
	        ids = pelicula.getProtagonistas().stream()
	                    .map(actor -> actor.getIdActor().toString())
	                    .collect(Collectors.joining(","));
	    }
		
		model.addAttribute("pelicula", pelicula); //<form th:action="@{/pelicula}" th:object="${pelicula}"...>
		
		model.addAttribute("ids", ids); //<input id="ids" name="ids" type="text" th:value="${ids}">
		
		model.addAttribute("titulo", "Editar Pelicula"); //<h2 th:text="${titulo}" class="py-4"></h2>
		
		model.addAttribute("generos", iGeneroService.findAllGeneros()); //Traemos los generos del service q inyectamos p/mostrarse en select del form(combo box)-> th:each="genero : ${generos}"
		
		model.addAttribute("actores", iActorService.findAllActores()); //Traemos los actores del service q inyectamos p/mostrarse en select del form -> <option th:each="actor : ${actores}"
		
		return "pelicula"; //nombre de la vista que va a retornar -> templates-> pelicula.html
	}
	
	@PostMapping("/pelicula") //botón guardar del form
	public String guardar(@Valid Pelicula pelicula, BindingResult br , @ModelAttribute(name = "ids") String ids, //En pelicula.html -> <input id="ids" name="ids" type="text">
			Model model, @RequestParam("archivo") MultipartFile imagen) { //<input type="file" id="archivo" name="archivo" onchange="previsualizar()">
		
		//Al momento de guardar preguntamos si el formulario tuvo algún error. Si hubo, enviamos nuevamente el form
		if(br.hasErrors()) {
			model.addAttribute("generos", iGeneroService.findAllGeneros()); 			
			model.addAttribute("actores", iActorService.findAllActores());
			return "pelicula"; //va a retornar la vista -> pelicula.html
		}
		
		//Para saber si recibimos una imagen
		if(!imagen.isEmpty()) { //si no viene vacío, guardamos la imagen
			String archivo = pelicula.getNombrePelicula() + getExtension(imagen.getOriginalFilename()); //obtenemos nombre del archivo y lo concatenamos con la extensión
			pelicula.setImagen(archivo); //lo agregamos al objeto Pelicula
			
			try {
				iArchivoService.guardar(archivo, imagen.getInputStream());
			} catch (IOException e) {				
				e.printStackTrace();
			} 
		}else {
			pelicula.setImagen("_default.jpg");
		}
		
		//creamos lista de tipo Long de idsProtagonistas separados por comas
		if (ids != null && !"".equals(ids)) {
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
	
	
	//Método privado:
	private String getExtension(String archivo) {
		return archivo.substring(archivo.lastIndexOf("."));
	}
	
	
	@GetMapping({"/", "/home", "/index"}) //Estas 3 opciones nos van a mandar a nuestro home.html donde estará el Catálogo
	public String home(Model model, @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer pagina) {
		
		PageRequest pr = PageRequest.of(pagina, 12); //cantidad de elementos a mostrar por pagina
		Page<Pelicula> page = iPeliculaService.findAllPeliculas(pr);
		
		//Agregamos las peliculas basandonos en el contenido del paginador		
		model.addAttribute("peliculas", page.getContent()); //devuelve el listado de peliculas
		
		//P/saber cuantas paginas tenemos y armarmamos un array de enteros p/las paginas
		if(page.getTotalPages() > 0) {
			List<Integer> paginas = IntStream.rangeClosed(1, page.getTotalPages()).boxed().toList();
			model.addAttribute("paginas", paginas); //mostramos las paginas
		}		
		//P/saber donde estamos parados
		model.addAttribute("actual", pagina + 1); //xq el indce de las paginas empieza en 0
		
		
		//header.html -> <div th:if="${msj != null}" th:text="${msj}" th:class="${tipoMsj != null ? 'alert alert-' + tipoMsj : 'alert'}">
		//Al presionar Catálogo en navbar:
		//model.addAttribute("msj", "Catálogo actualizado a 2024");
		//model.addAttribute("tipoMsj", "success"); //success -> clase bootstrap
		
		model.addAttribute("titulo", "Catálogo de peliculas");
		
		return "home"; //Vista que devolverá -> templates-> home.html
	}
	
	
	@GetMapping({"/listado"}) 
	public String listado(Model model, @RequestParam(required = false) String msj,  @RequestParam(required = false) String tipoMsj) {
		
		model.addAttribute("titulo", "Listado de Películas" );	
		
		//Añadimos/traemos las peliculas
		model.addAttribute("peliculas", iPeliculaService.findAllPeliculas());	
		
		if(!"".equals(tipoMsj) && !"".equals(msj)) { //si no están vacíos, los agregamos al model
			model.addAttribute("msj", msj);
			model.addAttribute("tipoMsj", tipoMsj);
			
		}
		
		return "listado"; //Vista que devolverá -> templates-> listado.html
	}
	
	
	@DeleteMapping("/pelicula/{id}/delete") 
	public String eliminar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAtt) { 
		
		iPeliculaService.delete(id);
		
		redirectAtt.addAttribute("msj", "La pelicula fue eliminada correctamente");
		redirectAtt.addAttribute("tipoMsj", "success");
		
		return "redirect:/listado"; 
	}

}
