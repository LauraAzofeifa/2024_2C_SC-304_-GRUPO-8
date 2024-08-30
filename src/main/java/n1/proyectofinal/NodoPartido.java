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
public class NodoPartido implements Serializable {

    private static final long serialVersionUID = 1L;


    private Partido partido;
    private NodoPartido siguiente;

    public NodoPartido(Partido partido) {
        this.partido = partido;
        this.siguiente = null;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public NodoPartido getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPartido siguiente) {
        this.siguiente = siguiente;
    }
    
    

}
