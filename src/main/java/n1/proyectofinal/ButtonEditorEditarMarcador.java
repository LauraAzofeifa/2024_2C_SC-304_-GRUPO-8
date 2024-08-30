package n1.proyectofinal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonEditorEditarMarcador extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private int selectedRow;
    private JTable table;
    private InterfazManager manager;
    private String nuevoMarcador;
    private ColaDePartidos colaPartidos;

    public ButtonEditorEditarMarcador(JCheckBox checkBox, JTable table, ColaDePartidos colaPartidos) {
        super(checkBox);
        this.table = table;  // Aquí se almacena la referencia a la tabla
        this.colaPartidos = colaPartidos;
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
            // Obtener los valores actuales de la fila seleccionada
            String equipo1 = (String) table.getValueAt(selectedRow, 0);
            String equipo2 = (String) table.getValueAt(selectedRow, 1);
            String marcadorActual = (String) table.getValueAt(selectedRow, 2);

            // Mostrar el diálogo para editar el marcador
            editarMarcadorDialog(equipo1, equipo2, marcadorActual);

            // Actualizar la tabla con el nuevo marcador
            table.setValueAt(nuevoMarcador, selectedRow, 2);
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

    private void editarMarcadorDialog(String equipo1, String equipo2, String marcadorActual) {
        JDialog dialog = new JDialog(manager, "Editar Marcador", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(manager);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(60, 63, 65));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtEquipo1 = new JTextField(equipo1, 25);
        txtEquipo1.setEditable(false);
        txtEquipo1.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEquipo1.setBackground(new Color(60, 63, 65));
        txtEquipo1.setForeground(Color.WHITE);
        txtEquipo1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtEquipo2 = new JTextField(equipo2, 25);
        txtEquipo2.setEditable(false);
        txtEquipo2.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEquipo2.setBackground(new Color(60, 63, 65));
        txtEquipo2.setForeground(Color.WHITE);
        txtEquipo2.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtMarcador = new JTextField(marcadorActual, 25);
        txtMarcador.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMarcador.setBackground(new Color(60, 63, 65));
        txtMarcador.setForeground(Color.WHITE);
        txtMarcador.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addFormField(panel, "Equipo 1:", txtEquipo1, 0, gbc);
        addFormField(panel, "Equipo 2:", txtEquipo2, 1, gbc);
        addFormField(panel, "Marcador:", txtMarcador, 2, gbc);

        JButton btnGuardar = createButton("Guardar", new Color(34, 139, 34), Color.WHITE);
        btnGuardar.addActionListener(e -> {
            nuevoMarcador = txtMarcador.getText();

            // Actualizar el objeto Partido correspondiente en la cola
            NodoPartido actual = colaPartidos.obtenerNodoFrente();
            while (actual != null) {
                Partido partido = actual.getPartido();
                if (partido.getEquipo1().equals(equipo1) && partido.getEquipo2().equals(equipo2)) {
                    partido.setResultado(nuevoMarcador);
                    break;
                }
                actual = actual.getSiguiente();
            }

            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(60, 63, 65));
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
