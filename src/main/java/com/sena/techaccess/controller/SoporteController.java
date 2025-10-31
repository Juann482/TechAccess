package com.sena.techaccess.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class SoporteController {

	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verSoporte(@PathVariable String filename) {
		try {
			Path archivo = Paths.get("uploads").resolve(filename).toAbsolutePath();
			Resource recurso = new UrlResource(archivo.toUri());

			if (!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo " + filename);
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
					.body(recurso);

		} catch (MalformedURLException e) {
			throw new RuntimeException("Error al cargar el archivo: " + filename, e);
		}
	}
}
