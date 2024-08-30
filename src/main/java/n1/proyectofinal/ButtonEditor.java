package n1.proyectofinal;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private boolean isPushed;
    private final InterfazManager manager;
    private final boolean isEditMode;
    private int row;

    public ButtonEditor(JCheckBox checkBox, InterfazManager manager, boolean isEditMode) {
        super(checkBox);
        this.manager = manager;
        this.isEditMode = isEditMode;
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
        button.addActionListener(e -> fireEditingStopped());
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
            int id = (int) manager.getEventTable().getValueAt(row, 0);
            if (isEditMode) {
                editarEvento(id);
            } else {
                int confirm = JOptionPane.showConfirmDialog(button, "¿Estás seguro de que deseas eliminar este evento?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.eliminarEvento(id);
                }
            }
        }
        isPushed = false;
        return label;
    }

    private void editarEvento(int id) {
        Evento evento = manager.getListaEventos().buscarEventoPorId(id);
        if (evento != null) {
            JTextField txtNombre = new JTextField(evento.getNombre());
            JTextField txtFecha = new JTextField(evento.getFecha());
            JTextField txtUbicacion = new JTextField(evento.getUbicacion());

            // Estilos para los campos de texto
            txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
            txtNombre.setBackground(new Color(60, 63, 65));
            txtNombre.setForeground(Color.WHITE);
            txtNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            txtFecha.setFont(new Font("Arial", Font.PLAIN, 14));
            txtFecha.setBackground(new Color(60, 63, 65));
            txtFecha.setForeground(Color.WHITE);
            txtFecha.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            txtUbicacion.setFont(new Font("Arial", Font.PLAIN, 14));
            txtUbicacion.setBackground(new Color(60, 63, 65));
            txtUbicacion.setForeground(Color.WHITE);
            txtUbicacion.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
            panel.add(createStyledLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(createStyledLabel("Fecha:"));
            panel.add(txtFecha);
            panel.add(createStyledLabel("Ubicación:"));
            panel.add(txtUbicacion);

            int result = JOptionPane.showConfirmDialog(button, panel, "Editar Evento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                evento.setNombre(txtNombre.getText());
                evento.setFecha(txtFecha.getText());
                evento.setUbicacion(txtUbicacion.getText());

                manager.updateEventTable();
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
        try {
            super.fireEditingStopped();
        } catch (Exception e) {
            // Manejo de la excepción, si es necesario
        }
    }
}
