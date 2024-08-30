/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Laura
 */
class EventTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Fecha", "Ubicación"};
    private final ListaDeEventos listaEventos;

    public EventTableModel(ListaDeEventos listaEventos) {
        this.listaEventos = listaEventos;
    }

    @Override
    public int getRowCount() {
        return listaEventos.contarEventos();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Evento[] eventos = listaEventos.obtenerEventos();
        Evento evento = eventos[row];
        switch (col) {
            case 0:
                return evento.getID();
            case 1:
                return evento.getNombre();
            case 2:
                return evento.getFecha();
            case 3:
                return evento.getUbicacion();
            default:
                return null;
        }
    }
}
