/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
public class PilaDePartidos {

    private NodoPartido cabeza;

    public PilaDePartidos() {
        this.cabeza = null;
    }

    // Método para apilar un partido
    public void apilar(Partido partido) {
        NodoPartido nuevoNodo = new NodoPartido(partido);
        if (this.cabeza == null) {
            this.cabeza = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(this.cabeza);
            this.cabeza = nuevoNodo;
        }
    }

    // Método para desapilar un partido
    public Partido desapilar() {
        if (this.cabeza == null) {
            return null;
        } else {
            Partido partido = this.cabeza.getPartido();
            this.cabeza = this.cabeza.getSiguiente();
            return partido;
        }
    }

    // Método para verificar si la pila está vacía
    public boolean estaVacia() {
        return this.cabeza == null;
    }

    // Método para obtener el partido en la cima de la pila sin desapilarlo
    public Partido obtenerCima() {
        if (this.cabeza == null) {
            return null;
        } else {
            return this.cabeza.getPartido();
        }
    }

    // Método para imprimir los partidos en la pila
    public void imprimirPila() {
        NodoPartido actual = this.cabeza;
        while (actual != null) {
            System.out.println(actual.getPartido().toString());
            actual = actual.getSiguiente();
        }
    }
}
