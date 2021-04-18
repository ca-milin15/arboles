package com.example.arbolb.demo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Nodo {

	List<Clave> listaClave = new ArrayList<>();
	int tamanoActual;
	boolean tipoNodoClave;

	public Nodo(Integer valor) {
		tipoNodoClave = true;
		tamanoActual = 1;
		listaClave.add(new Clave(valor));
	}

	public Nodo(Clave clave) {
		super();
		listaClave.add(clave);
		this.tamanoActual = 1;
	}
}
