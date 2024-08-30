/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

class ButtonEditorEliminarParticipante extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int selectedRow;
    private InterfazManager manager;

    public ButtonEditorEliminarParticipante(JCheckBox checkBox, InterfazManager manager) {
        super(checkBox);
        this.manager = manager;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        selectedRow = row;
        label = (value == null) ? "Eliminar" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if (selectedRow >= 0 && selectedRow < manager.getParticipantesTable().getRowCount()) {
                // Confirmación de eliminación
                int confirm = JOptionPane.showConfirmDialog(button, "¿Estás seguro de que deseas eliminar este participante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Detener cualquier edición antes de eliminar la fila
                    if (manager.getParticipantesTable().isEditing()) {
                        manager.getParticipantesTable().getCellEditor().stopCellEditing();
                    }

                    // Obtener el ID del participante a eliminar
                    int idParticipante = (int) manager.getParticipantesTable().getValueAt(selectedRow, 0);
                    manager.eliminarParticipante(idParticipante);

                    // Limpiar la selección y refrescar la tabla
                    manager.getParticipantesTable().clearSelection();
                    manager.updateParticipantesTable();
                }
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
