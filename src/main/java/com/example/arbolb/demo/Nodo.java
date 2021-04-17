package com.example.arbolb.demo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Nodo {

	List<Clave> listaClave = new ArrayList<>();
	int tamanoActual;

	public Nodo(Integer valor) {
		tamanoActual = 1;
		listaClave.add(new Clave(valor));
	}

	public Nodo(Clave clave) {
		super();
		listaClave.add(clave);
		this.tamanoActual = 1;
	}

	
}
