/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
import java.io.Serializable;

public class ListaDeEventos implements Serializable {
    private static final long serialVersionUID = 1L; 

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
        if (nodo.getEvento().getID() == id) {
            return nodo.getEvento(); // Encontrado
        }
        return buscarEventoPorId(nodo.getSiguiente(), id); // Seguir buscando
    }

    // Método para contar la cantidad de eventos en la lista
    public int contarEventos() {
        int contador = 0;
        NodoEvento actual = inicio;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    // Método para obtener todos los eventos en un array
    public Evento[] obtenerEventos() {
        int cantidad = contarEventos();
        Evento[] eventos = new Evento[cantidad];
        NodoEvento actual = inicio;
        int i = 0;
        while (actual != null) {
            eventos[i] = actual.getEvento();
            i++;
            actual = actual.getSiguiente();
        }
        return eventos;
    }

    // Método para eliminar un evento por ID
    public void eliminarEventoPorId(int id) {
        if (inicio == null) {
            return; // La lista está vacía
        }

        // Si el evento a eliminar es el primero de la lista
        if (inicio.getEvento().getID() == id) {
            inicio = inicio.getSiguiente();
            return;
        }

        // Recorrer la lista para encontrar el nodo a eliminar
        NodoEvento actual = inicio;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getEvento().getID() == id) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    public void eliminarParticipante(int id) {
        NodoEvento actual = inicio;

        // Recorrer todos los eventos en la lista
        while (actual != null) {
            Evento evento = actual.getEvento();
            ListaParticipantesDobleEnlazada listaParticipantes = evento.getLista();

            // Recorrer la lista de participantes del evento y eliminar si coincide el ID
            listaParticipantes.eliminarParticipantePorId(id);

            actual = actual.getSiguiente();
        }
    }

    public ListaParticipantesDobleEnlazada obtenerTodosLosParticipantes() {
        ListaParticipantesDobleEnlazada listaParticipantes = new ListaParticipantesDobleEnlazada();

        NodoEvento actual = inicio;

        // Recorrer todos los eventos en la lista
        while (actual != null) {
            Evento evento = actual.getEvento();
            ListaParticipantesDobleEnlazada listaDeParticipantesEvento = evento.getLista();

            // Recorrer la lista de participantes de cada evento
            NodoParticipante nodoParticipante = listaDeParticipantesEvento.getInicio();
            while (nodoParticipante != null) {
                listaParticipantes.agregarParticipante(nodoParticipante.getParticipante());
                nodoParticipante = nodoParticipante.getSiguiente();
            }

            actual = actual.getSiguiente();
        }

        return listaParticipantes;
    }
    
    public Participante buscarParticipanteEnEventos(int idParticipante) {
        NodoEvento actual = inicio;

        // Recorrer todos los eventos en la lista
        while (actual != null) {
            Evento evento = actual.getEvento();
            ListaParticipantesDobleEnlazada listaParticipantes = evento.getLista();

            // Buscar al participante en la lista de cada evento
            NodoParticipante nodoParticipante = listaParticipantes.getInicio();
            while (nodoParticipante != null) {
                Participante participante = nodoParticipante.getParticipante();
                if (participante.getID() == idParticipante) {
                    return participante; // Participante encontrado
                }
                nodoParticipante = nodoParticipante.getSiguiente();
            }

            actual = actual.getSiguiente();
        }

        return null; // Participante no encontrado
    }
    
     public void setEventos(Evento[] eventos) {
        this.inicio = null;  // Reinicia la lista actual

        for (Evento evento : eventos) {
            this.agregarEvento(evento);  // Agrega cada evento a la lista
        }
    }
}
