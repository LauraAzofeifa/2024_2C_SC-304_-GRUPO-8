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

public class NodoEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    private Evento evento;
    private NodoEvento siguiente;

    public NodoEvento(Evento evento) {
        this.evento = evento;
        this.siguiente = null;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public NodoEvento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoEvento siguiente) {
        this.siguiente = siguiente;
    }

}
