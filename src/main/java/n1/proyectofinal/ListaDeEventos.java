/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
public class ListaDeEventos {

    private NodoEvento inicio;

    public ListaDeEventos() {
        this.inicio = null;
    }

    // Método para agregar un evento al final de la lista
    public void agregarEvento(Evento evento) {
        NodoEvento nuevoNodo = new NodoEvento(evento);
        if (inicio == null) {
            inicio = nuevoNodo;
        } else {
            NodoEvento actual = inicio;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
    }

    // Método para eliminar un evento por nombre
    public void eliminarEvento(String nombre) {
        if (inicio == null) {
            return;
        }

        if (inicio.getEvento().getNombre().equals(nombre)) {
            inicio = inicio.getSiguiente();
            return;
        }

        NodoEvento actual = inicio;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getEvento().getNombre().equals(nombre)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    // Método para imprimir la lista de eventos
    public void imprimirLista() {
        NodoEvento actual = inicio;
        while (actual != null) {
            System.out.println(actual.getEvento());
            actual = actual.getSiguiente();
        }
    }
    
     // Método para buscar un evento por id
    public Evento buscarEventoPorId(int id) {
        return buscarEventoPorId(this.inicio, id);
    }

    private Evento buscarEventoPorId(NodoEvento nodo, int id) {
        if (nodo == null) {
            return null; // No encontrado
        }
        if (nodo.getEvento().getID()== id) {
            return nodo.getEvento(); // Encontrado
        }
        return buscarEventoPorId(nodo.getSiguiente(), id); // Seguir buscando
    }
}
