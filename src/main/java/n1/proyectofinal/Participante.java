/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
public class Participante {
    private String nombre;
    private int edad;
    private String equipo;

    public Participante(String nombre, int edad, String equipo) {
        this.nombre = nombre;
        this.edad = edad;
        this.equipo = equipo;
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
        return "Participante: " + 
                "Nombre=" + nombre + 
                "\\nEdad=" + edad + 
                "\\nEquipo=" + equipo + "\\n";
    }
    
    
}

