/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

/**
 *
 * @author Laura
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditorEliminarPartido extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private ColaDePartidos colaPartidos;
    private JPanel panelPartidos;
    private DefaultTableModel model;
    private int selectedRow;

    public ButtonEditorEliminarPartido(JCheckBox checkBox, ColaDePartidos colaPartidos, JPanel panelPartidos, DefaultTableModel model) {
        super(checkBox);
        this.colaPartidos = colaPartidos;
        this.panelPartidos = panelPartidos;
        this.model = model;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        selectedRow = row;
        label = (value == null) ? "Eliminar" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int confirm = JOptionPane.showConfirmDialog(button, "¿Estás seguro de que deseas eliminar este partido?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                colaPartidos.desencolar(); // Desencolar el partido correspondiente
                model.removeRow(selectedRow); // Eliminar la fila de la tabla
                panelPartidos.revalidate();
                panelPartidos.repaint();
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        try {
            super.fireEditingStopped();
        } catch (Exception e) {
        } finally {
        }

    }
}
