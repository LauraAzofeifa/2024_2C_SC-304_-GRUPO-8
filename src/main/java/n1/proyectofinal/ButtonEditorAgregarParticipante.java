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

public class ButtonEditorAgregarParticipante extends DefaultCellEditor {

    private final JButton button;
    private String label;
    private boolean isPushed;
    private final InterfazManager manager;
    private int row;

    public ButtonEditorAgregarParticipante(JCheckBox checkBox, InterfazManager manager) {
        super(checkBox);
        this.manager = manager;
        button = new JButton();
        button.setOpaque(true);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
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
            agregarParticipante(row);
        }
        isPushed = false;
        return label;
    }

    private void agregarParticipante(int row) {
        int idEvento = (int) manager.getEventTable().getValueAt(row, 0);
        Evento evento = manager.getListaEventos().buscarEventoPorId(idEvento);

        if (evento != null) {
            JTextField txtNombre = new JTextField();
            JTextField txtEdad = new JTextField();
            JTextField txtEquipo = new JTextField();

            // Estilos para los campos de texto
            txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
            txtNombre.setBackground(new Color(60, 63, 65));
            txtNombre.setForeground(Color.WHITE);
            txtNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            txtEdad.setFont(new Font("Arial", Font.PLAIN, 14));
            txtEdad.setBackground(new Color(60, 63, 65));
            txtEdad.setForeground(Color.WHITE);
            txtEdad.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            txtEquipo.setFont(new Font("Arial", Font.PLAIN, 14));
            txtEquipo.setBackground(new Color(60, 63, 65));
            txtEquipo.setForeground(Color.WHITE);
            txtEquipo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.setBackground(new Color(60, 63, 65));
            panel.add(createStyledLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(createStyledLabel("Edad:"));
            panel.add(txtEdad);
            panel.add(createStyledLabel("Equipo:"));
            panel.add(txtEquipo);

            int result = JOptionPane.showConfirmDialog(button, panel, "Agregar Participante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nombre = txtNombre.getText();
                    int edad = Integer.parseInt(txtEdad.getText());
                    String equipo = txtEquipo.getText();

                    Participante nuevoParticipante = new Participante(nombre, edad, equipo);
                    evento.getLista().agregarParticipante(nuevoParticipante);

                    JOptionPane.showMessageDialog(button, "Participante agregado exitosamente.");
                    manager.updateParticipantesTable();  // Actualizar la tabla de participantes si está visible
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(button, "Error: Edad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
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
