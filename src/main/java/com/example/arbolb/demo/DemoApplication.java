package com.example.arbolb.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		iniciarProcesoInsercionDato(12);
	}

	private static void iniciarProcesoInsercionDato(Integer valor) {
		try {
			var arbol = obtenerArbolAlmacenado();
			var arbolMutado = buscarUbicacionParaInsertar(arbol, valor);
			almacenarArbol(arbolMutado);
			System.out.println(arbol);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Arbol buscarUbicacionParaInsertar(Arbol arbol, Integer valor) {
		var nodoRaiz = arbol.getNodo();
		if (!ObjectUtils.isEmpty(nodoRaiz.getListaLlaves())) {
			for (int i = 0; i < nodoRaiz.getListaLlaves().size(); i++) {

			}
			return arbol;
		} else {
			nodoRaiz.getListaLlaves().add(valor);
			return arbol;
		}
	}

	private static void almacenarArbol(Arbol arbol) throws IOException {
		var arbolComoString = objectMapper.writeValueAsString(arbol);
		var rutaArchivo = Paths.get("C:\\Users\\camilo\\Downloads\\demo\\demo\\src\\main\\resources\\arbol.json");
		Files.write(rutaArchivo, arbolComoString.getBytes());
	}

	private static Arbol obtenerArbolAlmacenado() {
		var rutaArchivo = Paths.get("C:\\Users\\camilo\\Downloads\\demo\\demo\\src\\main\\resources\\arbol.json");
		var data = new StringBuilder();
		try (var reader = Files.newBufferedReader(rutaArchivo, Charset.forName("UTF-8"))) {
			var linea = new String();
			while ((linea = reader.readLine()) != null) {
				data.append(linea);
			}
			return objectMapper.readValue(data.toString(), Arbol.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
