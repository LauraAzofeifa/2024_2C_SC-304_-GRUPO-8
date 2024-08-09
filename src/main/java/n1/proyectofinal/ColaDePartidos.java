/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
public class ColaDePartidos {

    private NodoPartido cabeza;
    private NodoPartido cola;

    public ColaDePartidos() {
        this.cabeza = null;
        this.cola = null;
    }

    // Método para agregar un Partido a la cola
    public void encolar(Partido partido) {
        NodoPartido nuevoNodo = new NodoPartido(partido);
        if (cola == null) {
            cabeza = cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
    }

    // Método para eliminar un partido de la cola
    public Partido desencolar() {
        if (cabeza == null) {
            return null;
        } else {
            Partido partido = cabeza.getPartido();
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) {
                cola = null;
            }
            return partido;
        }
    }

    // Método para verificar si la cola está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }

    // Método para obtener el partido al inicio de la cola sin eliminarlo
    public Partido obtenerFrente() {
        if (cabeza == null) {
            return null;
        } else {
            return cabeza.getPartido();
        }
    }

    // Método para imprimir los partidos en la cola
    public void imprimirCola() {
        NodoPartido actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getPartido().toString());
            actual = actual.getSiguiente();
        }
    }
}
