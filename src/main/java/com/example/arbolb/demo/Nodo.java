package com.example.arbolb.demo;

import java.util.List;

public class Nodo {

	List<Integer> listaLlaves;
	List<Nodo> nodosIzquierda;
	List<Nodo> nodosDerecha;

	public List<Integer> getListaLlaves() {
		return listaLlaves;
	}

	public void setListaLlaves(List<Integer> listaLlaves) {
		this.listaLlaves = listaLlaves;
	}

	public List<Nodo> getNodosIzquierda() {
		return nodosIzquierda;
	}

	public void setNodosIzquierda(List<Nodo> nodosIzquierda) {
		this.nodosIzquierda = nodosIzquierda;
	}

	public List<Nodo> getNodosDerecha() {
		return nodosDerecha;
	}

	public void setNodosDerecha(List<Nodo> nodosDerecha) {
		this.nodosDerecha = nodosDerecha;
	}

}
