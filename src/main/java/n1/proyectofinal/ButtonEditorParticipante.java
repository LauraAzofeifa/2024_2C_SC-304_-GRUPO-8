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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditorParticipante extends DefaultCellEditor {

    private final JButton button;
    private String label;
    private boolean isPushed;
    private final InterfazManager manager;
    private final boolean isEditMode;
    private int row;

    public ButtonEditorParticipante(JCheckBox checkBox, InterfazManager manager, boolean isEditMode) {
        super(checkBox);
        this.manager = manager;
        this.isEditMode = isEditMode;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int id = (int) manager.getParticipantesTable().getValueAt(row, 0);
            if (isEditMode) {
                // Lógica para editar participante
                editarParticipante(id);
            } else {
                // Lógica para eliminar participante
                int confirm = JOptionPane.showConfirmDialog(button, "¿Estás seguro de que deseas eliminar este participante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.eliminarParticipante(id);
                }
            }
        }
        isPushed = false;
        return label;
    }

    private void editarParticipante(int id) {
        Participante participante = manager.getListaEventos().buscarParticipanteEnEventos(id);
        if (participante != null) {
            JTextField txtNombre = new JTextField(participante.getNombre());
            JTextField txtEdad = new JTextField(String.valueOf(participante.getEdad()));
            JTextField txtEquipo = new JTextField(participante.getEquipo());

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Edad:"));
            panel.add(txtEdad);
            panel.add(new JLabel("Equipo:"));
            panel.add(txtEquipo);

            int result = JOptionPane.showConfirmDialog(button, panel, "Editar Participante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                participante.setNombre(txtNombre.getText());
                participante.setEdad(Integer.parseInt(txtEdad.getText()));
                participante.setEquipo(txtEquipo.getText());
                manager.updateParticipantesTable();  // Método para actualizar la tabla de participantes
            }
        }
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
