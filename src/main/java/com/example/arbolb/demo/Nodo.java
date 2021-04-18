package com.example.arbolb.demo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
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

	public Nodo() {
		super();
		this.listaClave = new ArrayList<>();
		this.tamanoActual = 0;
		this.tipoNodoClave = true;
	}
}
