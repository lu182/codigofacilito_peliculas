package com.codigofacilito.peliculas.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ArchivoService implements IArchivoService {

	//@Override
	public void guardar(String archivo, InputStream bytes) {
		try {
			eliminar(archivo); //elimina el archivo/imagen si ya existe con el mismo nombre
			
			Files.copy(bytes, resolvePath(archivo)); //guarda el archivo
		
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}

	//@Override - //Método que utilizaremos p/recuperar las imagenes y lo haremos a traves de Http
	public ResponseEntity<Resource> get(String archivo) { //recibe nombre del archivo
		
		Resource resource = null;
		
		try {
			resource = new UrlResource(resolvePath(archivo).toUri());
		
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	//@Override
	public void eliminar(String archivo) {
		
		try {
			Files.deleteIfExists(resolvePath(archivo));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//Método privado:
	private Path resolvePath(String archivo) {
		return Paths.get("archivos").resolve(archivo).toAbsolutePath(); //nombre de la carpeta que creamos
	}	

}
