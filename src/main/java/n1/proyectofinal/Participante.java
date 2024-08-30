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
public class Participante implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nombre;
    private int edad;
    private String equipo;

    public Participante(String nombre, int edad, String equipo) {
        this.ID = generateNumberBasedOnDate();
        this.nombre = nombre;
        this.edad = edad;
        this.equipo = equipo;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Participante: "
                + "Nombre=" + nombre
                + "\\nEdad=" + edad
                + "\\nEquipo=" + equipo + "\\n";
    }

    public static int generateNumberBasedOnDate() {
        // Obtener la fecha y hora actual en milisegundos
        long currentTimeMillis = System.currentTimeMillis();

        // Convertir el tiempo en milisegundos a un hashcode
        int uniqueInt = Long.valueOf(currentTimeMillis).hashCode();

        // Asegurar que el número sea positivo
        uniqueInt = Math.abs(uniqueInt);

        return uniqueInt;
    }

}
