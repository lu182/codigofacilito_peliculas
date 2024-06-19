package com.codigofacilito.peliculas.services;

import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;


public interface IArchivoService {

	public void guardar(String archivo, InputStream bytes); //recibe el nombre del archivo
	public ResponseEntity<Resource> get(String archivo); //devuelve un ResponseEntity del recurso en sí
	
}
