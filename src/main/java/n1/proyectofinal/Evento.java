/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

import java.util.Date;

/**
 *
 * @author Laura
 */
public class Evento {

    private int ID;
    private String nombre;
    private Date fecha;
    private String ubicacion;
    private ListaParticipantesDobleEnlazada lista;
    private ColaDePartidos colapartidos;
    private PilaDePartidos historial;

    public Evento(int ID, String nombre, Date fecha, String ubicacion, ListaParticipantesDobleEnlazada lista, ColaDePartidos colapartidos, PilaDePartidos historial) {
        this.ID = ID;
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.lista = lista;
        this.colapartidos = colapartidos;
        this.historial = historial;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
        return "Evento:"
                + "\\nID=" + ID
                + "\\Nombre=" + nombre
                + "\\nFecha=" + fecha
                + "\\nUbicacion=" + ubicacion
                + "\\nLista=" + lista.toString()
                + "\\nPartidos=" + colapartidos.toString();
    }

}
