/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

import java.io.Serializable;

/**
 *
 * @author Laura
 */
public class ListaParticipantesDobleEnlazada implements Serializable {

    private static final long serialVersionUID = 1L;

    private NodoParticipante inicio;
    private NodoParticipante cola;

    public ListaParticipantesDobleEnlazada() {
        this.inicio = null;
        this.cola = null;
    }

    // Método para obtener el nodo inicial de la lista
    public NodoParticipante getInicio() {
        return inicio;
    }

    // Método para agregar un participante al final de la lista
    public void agregarParticipante(Participante participante) {
        NodoParticipante nuevoNodo = new NodoParticipante(participante);
        if (inicio == null) {
            inicio = cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
    }

    // Método para eliminar un participante por nombre
    public void eliminarParticipante(String nombre) {
        NodoParticipante actual = inicio;
        while (actual != null) {
            if (actual.getParticipante().getNombre().equals(nombre)) {
                if (actual.getAnterior() != null) {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                } else {
                    inicio = actual.getSiguiente();
                }
                if (actual.getSiguiente() != null) {
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                } else {
                    cola = actual.getAnterior();
                }
                break;
            }
            actual = actual.getSiguiente();
        }
    }

    // Método para imprimir la lista desde el inicio
    public void imprimirDesdeInicio() {
        NodoParticipante actual = inicio;
        while (actual != null) {
            System.out.println(actual.getParticipante());
            actual = actual.getSiguiente();
        }
    }

    // Método para imprimir la lista desde el final
    public void imprimirDesdeFinal() {
        NodoParticipante actual = cola;
        while (actual != null) {
            System.out.println(actual.getParticipante());
            actual = actual.getAnterior();
        }
    }

    // Método recursivo para imprimir la lista desde el inicio
    public void imprimirDesdeInicioRecursivo() {
        imprimirDesdeInicioRecursivo(this.inicio);
    }

    private void imprimirDesdeInicioRecursivo(NodoParticipante nodo) {
        if (nodo != null) {
            System.out.println(nodo.getParticipante().toString());
            imprimirDesdeInicioRecursivo(nodo.getSiguiente());
        }
    }

    // Método recursivo para buscar un participante por nombre
    public Participante buscarParticipanteRecursivo(String nombre) {
        return buscarParticipanteRecursivo(this.inicio, nombre);
    }

    private Participante buscarParticipanteRecursivo(NodoParticipante nodo, String nombre) {
        if (nodo == null) {
            return null; // No encontrado
        }
        if (nodo.getParticipante().getNombre().equals(nombre)) {
            return nodo.getParticipante(); // Encontrado
        }
        return buscarParticipanteRecursivo(nodo.getSiguiente(), nombre); // Seguir buscando
    }

    // Método para eliminar un participante por ID
    public void eliminarParticipantePorId(int id) {
        NodoParticipante actual = inicio;

        while (actual != null) {
            if (actual.getParticipante().getID() == id) {
                if (actual.getAnterior() != null) {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                } else {
                    inicio = actual.getSiguiente();
                }
                if (actual.getSiguiente() != null) {
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                } else {
                    cola = actual.getAnterior();
                }
                break;
            }
            actual = actual.getSiguiente();
        }
    }

}
