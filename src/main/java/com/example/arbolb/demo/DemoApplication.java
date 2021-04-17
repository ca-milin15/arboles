package com.example.arbolb.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

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
		iniciarProcesoInsercionDato(5);
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
		if (!ObjectUtils.isEmpty(nodoRaiz)) {
			var listaClaves = nodoRaiz.getListaClave();
			if (listaClaves.stream().anyMatch(clave -> clave.getValorClave() == valor)) {
				throw new RuntimeException("El valor a insertar ya existe en el arbol");
			}
			for (int i = 0; i < listaClaves.size(); i++) {
				var siguienteClave = obtenerClaveSiguiente(listaClaves, i);
				if (listaClaves.get(i).getValorClave() > valor) {
					if (listaClaves.get(i).getNodoIzquierda().getTamanoActual() == 0) {
						arbol.setNodo(insercionEnHoja(nodoRaiz, listaClaves, valor, siguienteClave));
						break;
					}
				} else if (valor > listaClaves.get(i).getValorClave()
						&& (!ObjectUtils.isEmpty(siguienteClave) && valor < siguienteClave.getValorClave())) {
					if (listaClaves.get(i).getNodoDerecha().getTamanoActual() == 0
							&& siguienteClave.getNodoIzquierda().getTamanoActual() == 0) {
						arbol.setNodo(insercionEnHoja(nodoRaiz, listaClaves, valor, siguienteClave));
						break;
					}
				} else if (valor > listaClaves.get(i).getValorClave() && ObjectUtils.isEmpty(siguienteClave)) {
					if (listaClaves.get(i).getNodoIzquierda().getTamanoActual() == 0) {
						arbol.setNodo(insercionEnHoja(nodoRaiz, listaClaves, valor, siguienteClave));
						break;
					}
				}
			}
		} else {
			arbol.setNodo(new Nodo(valor));
		}

		/*
		 * if (!ObjectUtils.isEmpty(nodoRaiz.getListaLlaves())) { for (int i = 0; i <
		 * nodoRaiz.getListaLlaves().size(); i++) {
		 * 
		 * } return arbol; } else { nodoRaiz.getListaLlaves().add(valor); return arbol;
		 * }
		 */
		return arbol;
	}

	private static Clave obtenerClaveSiguiente(List<Clave> listaClaves, int i) {
		try {
			return listaClaves.get(i + 1);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	private static Nodo insercionEnHoja(Nodo nodoRaiz, List<Clave> listaClaves, Integer valor, Clave siguienteClave) {
		listaClaves.add(new Clave(valor));
		nodoRaiz.setTamanoActual(nodoRaiz.getTamanoActual() + 1);
		Collections.sort(listaClaves, (ref1, ref2) -> ref1.getValorClave().compareTo(ref2.getValorClave()));
		System.out.println(listaClaves);
		var posibleNuevoNodo = validarDimensionHoja(nodoRaiz);
		if (!ObjectUtils.isEmpty(posibleNuevoNodo))
			nodoRaiz = posibleNuevoNodo;
		return nodoRaiz;
	}

	private static Nodo validarDimensionHoja(Nodo nodoRaiz) {
		if (nodoRaiz.getTamanoActual() > 4) {
			var nodoAAscender = nodoRaiz.getListaClave().get(2);
			var nodoIzquierdo = crearNodo(nodoRaiz.getListaClave(), 0, 2);
			var nodoDerecho = crearNodo(nodoRaiz.getListaClave(), 2, 5);

			return new Nodo(new Clave(nodoAAscender.getValorClave(), nodoIzquierdo, nodoDerecho));
		}
		return null;
	}

	private static Nodo crearNodo(List<Clave> listaClave, int valorInicial, int valorFinal) {
		var nodo = new Nodo();
		for (var i = valorInicial; i < valorFinal; i++) {
			nodo.getListaClave().add(new Clave(listaClave.get(i).getValorClave()));
		}
		return nodo;
	}

//	private static void reordenar (int actual, List<Clave> listaClaves, Clave claveActual, Clave nuevaClave) {
//		try {
//			boolean finalLista;
//			try {
//				var valorFlotante = listaClaves.get(actual + 1);
//			} catch (IndexOutOfBoundsException e) {
//				finalLista = true; 
//			}
//			listaClaves.add(actual + 1, claveActual);
//			listaClaves.add(actual, nuevaClave);
//			reordenar(actual + 1, listaClaves, listaClaves.get(actual + 1),  );
//		} catch (IndexOutOfBoundsException e) {
//			// TODO: handle exception
//		}
//	}
//	private static void insercionEnHoja(Nodo nodoRaiz, List<Clave> listaClaves, Integer valor, Clave siguienteClave) {
//		for (int i = 0; i < listaClaves.size(); i++) {
//			if(listaClaves.get(i).getValorClave() > valor) {
//				reordenar(i, listaClaves, listaClaves.get(i), new Clave(valor));
//				nodoRaiz.setTamanoActual(nodoRaiz.getTamanoActual() + 1);
//				break;
//			} else if ( valor > listaClaves.get(i).getValorClave() && 
//					(!ObjectUtils.isEmpty(siguienteClave) && valor < siguienteClave.getValorClave())) {
//				listaClaves.add(i+1, new Clave(valor));
//				nodoRaiz.setTamanoActual(nodoRaiz.getTamanoActual() + 1);
//				break;
//			} else if (valor > listaClaves.get(i).getValorClave() && ObjectUtils.isEmpty(siguienteClave)) {
//				if(CollectionUtils.isEmpty(listaClaves.get(i).getNodosIzquierda())) {
//					insercionEnHoja(nodoRaiz, listaClaves, valor, siguienteClave);
//					break;
//				}
//			} 
//		}
//	}

	private static void almacenarArbol(Arbol arbol) throws IOException {
		var arbolComoString = objectMapper.writeValueAsString(arbol);
		var rutaArchivo = Paths
				.get("C:\\Users\\camilo\\Documents\\Proyectos Software\\arbolB\\src\\main\\resources\\arbol.json");
		Files.write(rutaArchivo, arbolComoString.getBytes());
	}

	private static Arbol obtenerArbolAlmacenado() {
		var rutaArchivo = Paths
				.get("C:\\Users\\camilo\\Documents\\Proyectos Software\\arbolB\\src\\main\\resources\\arbol.json");
		var data = new StringBuilder();
		try (var reader = Files.newBufferedReader(rutaArchivo, Charset.forName("UTF-8"))) {
			var linea = new String();
			while ((linea = reader.readLine()) != null) {
				data.append(linea);
			}
			if (!ObjectUtils.isEmpty(data)) {
				return objectMapper.readValue(data.toString(), Arbol.class);
			}
			return new Arbol();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
