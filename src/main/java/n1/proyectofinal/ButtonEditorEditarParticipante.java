package n1.proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

class ButtonEditorEditarParticipante extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int selectedRow;
    private final InterfazManager manager;

    public ButtonEditorEditarParticipante(JCheckBox checkBox, InterfazManager manager) {
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
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        selectedRow = row;
        label = (value == null) ? "Editar" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int idParticipante = (int) manager.getParticipantesTable().getValueAt(selectedRow, 0);
            String nombreParticipante = (String) manager.getParticipantesTable().getValueAt(selectedRow, 1);
            int edadParticipante = (int) manager.getParticipantesTable().getValueAt(selectedRow, 2);
            String equipoParticipante = (String) manager.getParticipantesTable().getValueAt(selectedRow, 3);

            editarParticipanteDialog(idParticipante, nombreParticipante, edadParticipante, equipoParticipante);
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
        super.fireEditingStopped();
    }

    private void editarParticipanteDialog(int id, String nombre, int edad, String equipo) {
        JDialog dialog = new JDialog(manager, "Editar Participante", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(manager);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtID = new JTextField(String.valueOf(id));
        txtID.setEditable(false);
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setBackground(new Color(60, 63, 65));
        txtID.setForeground(Color.WHITE);
        txtID.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtNombre = new JTextField(nombre);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBackground(new Color(60, 63, 65));
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtEdad = new JTextField(String.valueOf(edad));
        txtEdad.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEdad.setBackground(new Color(60, 63, 65));
        txtEdad.setForeground(Color.WHITE);
        txtEdad.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtEquipo = new JTextField(equipo);
        txtEquipo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEquipo.setBackground(new Color(60, 63, 65));
        txtEquipo.setForeground(Color.WHITE);
        txtEquipo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addFormField(panel, "ID:", txtID, 0, gbc);
        addFormField(panel, "Nombre:", txtNombre, 1, gbc);
        addFormField(panel, "Edad:", txtEdad, 2, gbc);
        addFormField(panel, "Equipo:", txtEquipo, 3, gbc);

        JButton btnGuardar = createButton("Guardar", new Color(34, 139, 34), Color.WHITE);
        btnGuardar.addActionListener(e -> {
            manager.getParticipantesTable().setValueAt(txtNombre.getText(), selectedRow, 1);
            manager.getParticipantesTable().setValueAt(Integer.parseInt(txtEdad.getText()), selectedRow, 2);
            manager.getParticipantesTable().setValueAt(txtEquipo.getText(), selectedRow, 3);
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel de botones
        buttonPanel.add(btnGuardar);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    private void addFormField(JPanel formPanel, String label, Component field, int yPos, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lbl, gbc);
        gbc.gridx = 1;
        formPanel.add(field, gbc);
    }
}
