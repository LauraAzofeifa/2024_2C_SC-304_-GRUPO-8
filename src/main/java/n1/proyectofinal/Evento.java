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

public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nombre;
    private String fecha;
    private String ubicacion;
    private ListaParticipantesDobleEnlazada lista;
    private ColaDePartidos colapartidos;
    private PilaDePartidos historial;

    public Evento(int ID, String nombre, String fecha, String ubicacion, ListaParticipantesDobleEnlazada lista, ColaDePartidos colapartidos, PilaDePartidos historial) {
        this.ID = ID;
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.lista = lista != null ? lista : new ListaParticipantesDobleEnlazada(); // Inicializar como lista vacía si es null
        this.colapartidos = colapartidos != null ? colapartidos : new ColaDePartidos(); // Inicializar como cola vacía si es null
        this.historial = historial != null ? historial : new PilaDePartidos(); // Inicializar como pila vacía si es null
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ListaParticipantesDobleEnlazada getLista() {
        return lista;
    }

    public void setLista(ListaParticipantesDobleEnlazada lista) {
        this.lista = lista;
    }

    public ColaDePartidos getColapartidos() {
        return colapartidos;
    }

    public void setColapartidos(ColaDePartidos colapartidos) {
        this.colapartidos = colapartidos;
    }

    public PilaDePartidos getHistorial() {
        return historial;
    }

    public void setHistorial(PilaDePartidos historial) {
        this.historial = historial;
    }

    @Override
    public String toString() {
        return "ID " + ID
                + " Nombre=" + nombre;
    }
}
