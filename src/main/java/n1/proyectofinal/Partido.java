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
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    private String equipo1;
    private String equipo2;
    private String resultado;

    public Partido(String equipo1, String equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.resultado = "";
    }

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Partido:" + 
                "\\nEquipo1=" + equipo1 
                + "\\nEquipo2=" + equipo2 
                + "\\nResultado=" + resultado ;
    }
}

