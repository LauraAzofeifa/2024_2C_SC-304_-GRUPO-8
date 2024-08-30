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
public class NodoParticipante implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Participante participante;
    private NodoParticipante siguiente;
    private NodoParticipante anterior;

    public NodoParticipante(Participante datos) {
        this.participante = datos;
        this.siguiente = null;
        this.anterior = null;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public NodoParticipante getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoParticipante siguiente) {
        this.siguiente = siguiente;
    }

    public NodoParticipante getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoParticipante anterior) {
        this.anterior = anterior;
    }
}
