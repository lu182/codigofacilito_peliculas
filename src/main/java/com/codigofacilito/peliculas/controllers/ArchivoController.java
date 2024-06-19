package com.codigofacilito.peliculas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codigofacilito.peliculas.services.IArchivoService;

@Controller
public class ArchivoController {
	
	//Inyectamos el servicio
	@Autowired
	private IArchivoService iArchivoService;
	
	
	@GetMapping("/archivo") //lo declaramos asi p/saber a donde ir a buscar el archivo y le decimos que vaya a /archivo
	public ResponseEntity<Resource> get(@RequestParam("n") String archivo){ //nos interesa recibir el nombre del archivo -> n de name
		return iArchivoService.get(archivo);
	}

}
