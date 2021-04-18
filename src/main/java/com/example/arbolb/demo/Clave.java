package com.example.arbolb.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Clave {
 
	
	Integer valorClave;
	Nodo nodoIzquierda;
	Nodo nodoDerecha;

	public Clave(Integer valorClave) {
		this.valorClave = valorClave;
		this.nodoIzquierda = new Nodo();
		this.nodoDerecha = new Nodo();
	}

}
