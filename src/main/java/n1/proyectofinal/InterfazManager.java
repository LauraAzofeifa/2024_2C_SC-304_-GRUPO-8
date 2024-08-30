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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

public class InterfazManager extends JFrame {

    private final JTextField txtID;
    private final JTextField txtNombre;
    private final JTextField txtUbicacion;
    private final JButton btnGuardar;
    private Evento evento;
    private final JPanel mainPanel;
    private JPanel tablaPanel;
    private JPanel tablaParticipantesPanel;
    private ListaDeEventos listaEventos;
    private JTable eventTable;
    private DefaultTableModel eventTableModel;
    private JTable participantesTable;
    private DefaultTableModel participantesTableModel;
    private JDatePickerImpl datePicker;
    private JComboBox<Evento> comboBoxEventos;
    private JComboBox<Evento> comboBoxVerPartidos;

    public InterfazManager(ListaDeEventos listaEventos) {
        this.listaEventos = listaEventos;
        setTitle("Gestión de Eventos Deportivos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel principal

        txtID = new JTextField(20);
        txtNombre = new JTextField(20);
        txtUbicacion = new JTextField(20);
        btnGuardar = createButton("Guardar", new Color(34, 139, 34), Color.WHITE);

        datePicker = createDatePicker();

        initMainPanel();
        initTablaPanel();
        initTablaParticipantesPanel();
        updateEventTable();

        add(mainPanel);
    }

    private void initMainPanel() {
        mainPanel.add(createTitleLabel("Agregar Evento"), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarEvento());
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        buttonPanel.add(btnGuardar);
        return buttonPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(43, 43, 43));  // Fondo oscuro del panel de formulario
        formPanel.setBorder(createTitledBorder("Datos del Evento"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormField(formPanel, "ID:", txtID, 0, gbc);
        txtID.setEditable(false);
        txtID.setBackground(new Color(60, 63, 65));
        txtID.setForeground(Color.WHITE);
        txtID.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtID.setText(generateNumberBasedOnDate() + "");

        addFormField(formPanel, "Nombre:", txtNombre, 1, gbc);
        txtNombre.setBackground(new Color(60, 63, 65));
        txtNombre.setForeground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addFormField(formPanel, "Fecha:", datePicker, 2, gbc);
        addFormField(formPanel, "Ubicación:", txtUbicacion, 3, gbc);
        txtUbicacion.setBackground(new Color(60, 63, 65));
        txtUbicacion.setForeground(Color.WHITE);
        txtUbicacion.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        return formPanel;
    }

    private void initTablaPanel() {
        tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel de la tabla
        tablaPanel.setBorder(createTitledBorder("Lista de Eventos"));

        eventTableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Fecha", "Ubicación", "Agregar Participante", "Editar", "Eliminar"}, 0);
        eventTable = createEventTable();
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Borde gris del JScrollPane
        tablaPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JTable createEventTable() {
        JTable table = new JTable(eventTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4;
            }
        };

        customizeTable(table);
        table.getColumn("Agregar Participante").setCellRenderer(new ButtonRenderer());
        table.getColumn("Agregar Participante").setCellEditor(new ButtonEditorAgregarParticipante(new JCheckBox(), this));
        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), this, true));
        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), this, false));

        return table;
    }

    private void initTablaParticipantesPanel() {
        tablaParticipantesPanel = new JPanel(new BorderLayout());
        tablaParticipantesPanel.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel de participantes
        tablaParticipantesPanel.setBorder(createTitledBorder("Lista de Participantes"));

        participantesTableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Edad", "Equipo", "Editar", "Eliminar"}, 0);
        participantesTable = new JTable(participantesTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4;
            }
        };

        customizeTable(participantesTable);

        participantesTable.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        participantesTable.getColumn("Editar").setCellEditor(new ButtonEditorEditarParticipante(new JCheckBox(), this));

        participantesTable.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        participantesTable.getColumn("Eliminar").setCellEditor(new ButtonEditorEliminarParticipante(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(participantesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Borde gris del JScrollPane
        tablaParticipantesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private TitledBorder createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                Color.WHITE
        );
    }

    public void updateEventTable() {
        eventTableModel.setRowCount(0);

        for (Evento evento : listaEventos.obtenerEventos()) {
            eventTableModel.addRow(new Object[]{evento.getID(), evento.getNombre(), evento.getFecha(), evento.getUbicacion(), "Agregar Participante", "Editar", "Eliminar"});
        }
    }

    public void updateParticipantesTable() {
        participantesTableModel.setRowCount(0);

        for (NodoParticipante actual = listaEventos.obtenerTodosLosParticipantes().getInicio(); actual != null; actual = actual.getSiguiente()) {
            Participante participante = actual.getParticipante();
            participantesTableModel.addRow(new Object[]{participante.getID(), participante.getNombre(), participante.getEdad(), participante.getEquipo(), "Editar", "Eliminar"});
        }
    }

    private void guardarEvento() {
        try {
            int id = Integer.parseInt(txtID.getText());
            String nombre = txtNombre.getText();
            String fecha = datePicker.getModel().getValue().toString();
            String ubicacion = txtUbicacion.getText();

            evento = new Evento(id, nombre, fecha, ubicacion, new ListaParticipantesDobleEnlazada(), new ColaDePartidos(), new PilaDePartidos());
            listaEventos.agregarEvento(evento);

            resetForm();
            JOptionPane.showMessageDialog(this, "Evento guardado exitosamente.");
            updateEventTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el evento. Verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void resetForm() {
        txtID.setText(generateNumberBasedOnDate() + "");
        txtNombre.setText("");
        txtUbicacion.setText("");
    }

    public void editarParticipanteEnLista(int id, String nuevoNombre, int nuevaEdad, String nuevoEquipo) {
        NodoParticipante actual = listaEventos.obtenerTodosLosParticipantes().getInicio();
        while (actual != null) {
            if (actual.getParticipante().getID() == id) {
                actual.getParticipante().setNombre(nuevoNombre);
                actual.getParticipante().setEdad(nuevaEdad);
                actual.getParticipante().setEquipo(nuevoEquipo);
                break;
            }
            actual = actual.getSiguiente();
        }
    }

    public void eliminarParticipante(int id) {
        listaEventos.eliminarParticipante(id);
        updateParticipantesTable();
    }

    private JLabel createTitleLabel(String title) {
        JLabel lblTitulo = new JLabel(title, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return lblTitulo;
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

    private JDatePickerImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setBackground(new Color(60, 63, 65));
        datePicker.getJFormattedTextField().setForeground(Color.WHITE);
        datePicker.getJFormattedTextField().setBorder(BorderFactory.createLineBorder(Color.GRAY));
        datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.PLAIN, 14));
        return datePicker;
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

    private void customizeTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.GRAY);
        table.setBackground(new Color(43, 43, 43));
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(0, 120, 215));
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(0, 120, 215));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void eliminarEvento(int id) {
        Evento eventoAEliminar = listaEventos.buscarEventoPorId(id);

        if (eventoAEliminar != null) {
            listaEventos.eliminarEventoPorId(id);
            updateEventTable();
            JOptionPane.showMessageDialog(this, "Evento eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel crearPanelBusquedaParticipante() {
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        panelBusqueda.setBorder(createTitledBorder("Consulta de Participantes"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("Ingrese el ID del Participante:");
        lblID.setForeground(Color.WHITE);
        lblID.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField txtIDBusqueda = new JTextField(10);
        txtIDBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));
        txtIDBusqueda.setBackground(new Color(60, 63, 65));
        txtIDBusqueda.setForeground(Color.WHITE);
        txtIDBusqueda.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton btnBuscar = createButton("Buscar", new Color(0, 120, 215), Color.WHITE);

        JTextArea txtResultado = new JTextArea(5, 20);
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        txtResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        txtResultado.setBackground(new Color(43, 43, 43));
        txtResultado.setForeground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBusqueda.add(lblID, gbc);

        gbc.gridx = 1;
        panelBusqueda.add(txtIDBusqueda, gbc);

        gbc.gridx = 2;
        panelBusqueda.add(btnBuscar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panelBusqueda.add(new JScrollPane(txtResultado), gbc);

        btnBuscar.addActionListener(e -> {
            String idText = txtIDBusqueda.getText().trim();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(panelBusqueda, "Por favor, ingrese un ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                Participante participante = buscarParticipantePorId(listaEventos.obtenerTodosLosParticipantes().getInicio(), id);

                txtResultado.setText("");

                if (participante != null) {
                    txtResultado.setText("ID: " + participante.getID() + "\n"
                            + "Nombre: " + participante.getNombre() + "\n"
                            + "Edad: " + participante.getEdad() + "\n"
                            + "Equipo: " + participante.getEquipo());
                } else {
                    txtResultado.setText("No se encontró ningún participante con el ID " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelBusqueda, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelBusqueda;
    }

    private Participante buscarParticipantePorId(NodoParticipante actual, int id) {
        if (actual == null) {
            return null;
        }

        if (actual.getParticipante().getID() == id) {
            return actual.getParticipante();
        }

        return buscarParticipantePorId(actual.getSiguiente(), id);
    }

    public JPanel crearPanelBusquedaEvento() {
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        panelBusqueda.setBorder(createTitledBorder("Consulta de Eventos"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblID = new JLabel("Ingrese el ID del Evento:");
        lblID.setForeground(Color.WHITE);
        lblID.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtIDBusqueda = new JTextField(40);
        txtIDBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));
        txtIDBusqueda.setBackground(new Color(60, 63, 65));
        txtIDBusqueda.setForeground(Color.WHITE);
        txtIDBusqueda.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton btnBuscar = createButton("Buscar", new Color(0, 120, 215), Color.WHITE);

        JTextArea txtResultadoEvento = new JTextArea(5, 20);
        txtResultadoEvento.setEditable(false);
        txtResultadoEvento.setLineWrap(true);
        txtResultadoEvento.setWrapStyleWord(true);
        txtResultadoEvento.setFont(new Font("Arial", Font.PLAIN, 14));
        txtResultadoEvento.setBackground(new Color(43, 43, 43));
        txtResultadoEvento.setForeground(Color.WHITE);
        txtResultadoEvento.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        DefaultTableModel participantesTableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Edad", "Equipo"}, 0);
        JTable tablaParticipantes = new JTable(participantesTableModel);
        tablaParticipantes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaParticipantes.setRowHeight(30);
        tablaParticipantes.setGridColor(Color.GRAY);
        tablaParticipantes.setBackground(new Color(43, 43, 43));
        tablaParticipantes.setForeground(Color.WHITE);
        tablaParticipantes.getTableHeader().setBackground(new Color(0, 120, 215));
        tablaParticipantes.getTableHeader().setForeground(Color.WHITE);
        tablaParticipantes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaParticipantes.getColumnCount(); i++) {
            tablaParticipantes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPaneTabla = new JScrollPane(tablaParticipantes);
        scrollPaneTabla.setBorder(BorderFactory.createLineBorder(Color.GRAY));  // Borde gris del JScrollPane

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBusqueda.add(lblID, gbc);

        gbc.gridx = 1;
        panelBusqueda.add(txtIDBusqueda, gbc);

        gbc.gridx = 2;
        panelBusqueda.add(btnBuscar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panelBusqueda.add(new JScrollPane(txtResultadoEvento), gbc);

        gbc.gridy = 2;
        panelBusqueda.add(scrollPaneTabla, gbc);

        btnBuscar.addActionListener(e -> {
            String idText = txtIDBusqueda.getText().trim();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(panelBusqueda, "Por favor, ingrese un ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                Evento evento = listaEventos.buscarEventoPorId(id);

                txtResultadoEvento.setText("");
                participantesTableModel.setRowCount(0);

                if (evento != null) {
                    txtResultadoEvento.setText("ID: " + evento.getID() + "\n"
                            + "Nombre: " + evento.getNombre() + "\n"
                            + "Fecha: " + evento.getFecha() + "\n"
                            + "Ubicación: " + evento.getUbicacion());

                    NodoParticipante actual = evento.getLista().getInicio();
                    while (actual != null) {
                        Participante p = actual.getParticipante();
                        participantesTableModel.addRow(new Object[]{p.getID(), p.getNombre(), p.getEdad(), p.getEquipo()});
                        actual = actual.getSiguiente();
                    }
                } else {
                    txtResultadoEvento.setText("No se encontró ningún evento con el ID " + id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelBusqueda, "ID inválido. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelBusqueda;
    }

    public JPanel crearPanelGestionPartidos() {
        JPanel panelGestionPartidos = new JPanel(new GridBagLayout());
        panelGestionPartidos.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        panelGestionPartidos.setBorder(createTitledBorder("Gestión de Partidos"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta para seleccionar evento
        JLabel lblSeleccionarEvento = createStyledLabel("Seleccionar Evento:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelGestionPartidos.add(lblSeleccionarEvento, gbc);

        // ComboBox para seleccionar el evento
        comboBoxEventos = new JComboBox<>();
        comboBoxEventos.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBoxEventos.setBackground(new Color(60, 63, 65));
        comboBoxEventos.setForeground(Color.WHITE);
        comboBoxEventos.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Cargar eventos y verificar si hay eventos disponibles
        cargarEventosEnComboBox(comboBoxEventos);

        gbc.gridx = 1;
        panelGestionPartidos.add(comboBoxEventos, gbc);

        // Panel para gestionar partidos, se actualizará cuando se seleccione un evento
        JPanel panelPartidos = new JPanel(new GridBagLayout());
        panelPartidos.setBackground(new Color(60, 63, 65));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelGestionPartidos.add(panelPartidos, gbc);

        // Listener para actualizar el panel cuando se selecciona un evento
        comboBoxEventos.addActionListener(e -> {
            Evento eventoSeleccionado = (Evento) comboBoxEventos.getSelectedItem();
            if (eventoSeleccionado != null && eventoSeleccionado.getID() != -1) {
                actualizarPanelPartidos(panelPartidos, eventoSeleccionado);
            } else {
                panelPartidos.removeAll();
                panelPartidos.add(createStyledLabel("Seleccione un evento válido."));
                panelPartidos.revalidate();
                panelPartidos.repaint();
            }
        });

        return panelGestionPartidos;
    }

    private void actualizarPanelPartidos(JPanel panelPartidos, Evento evento) {
        panelPartidos.removeAll();

        ColaDePartidos colaPartidos = evento.getColapartidos();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos para los equipos
        JTextField txtEquipo1 = new JTextField(20);
        txtEquipo1.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEquipo1.setBackground(new Color(60, 63, 65));
        txtEquipo1.setForeground(Color.WHITE);
        txtEquipo1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTextField txtEquipo2 = new JTextField(20);
        txtEquipo2.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEquipo2.setBackground(new Color(60, 63, 65));
        txtEquipo2.setForeground(Color.WHITE);
        txtEquipo2.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Botón para agregar un nuevo partido
        JButton btnAgregarPartido = createButton("Agregar Partido", new Color(34, 139, 34), Color.WHITE);
        btnAgregarPartido.addActionListener(e -> {
            String equipo1 = txtEquipo1.getText().trim();
            String equipo2 = txtEquipo2.getText().trim();
            if (!equipo1.isEmpty() && !equipo2.isEmpty()) {
                Partido nuevoPartido = new Partido(equipo1, equipo2);
                colaPartidos.encolar(nuevoPartido);
                actualizarListaPartidos(panelPartidos, colaPartidos);
                txtEquipo1.setText("");
                txtEquipo2.setText("");
            } else {
                JOptionPane.showMessageDialog(panelPartidos, "Por favor, ingrese los nombres de ambos equipos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPartidos.add(createStyledLabel("Equipo 1:"), gbc);
        gbc.gridx = 1;
        panelPartidos.add(txtEquipo1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPartidos.add(createStyledLabel("Equipo 2:"), gbc);
        gbc.gridx = 1;
        panelPartidos.add(txtEquipo2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panelPartidos.add(btnAgregarPartido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panelPartidos.add(createStyledLabel("Lista de Partidos:"), gbc);

        // Crear y configurar la tabla de partidos
        actualizarListaPartidos(panelPartidos, colaPartidos);

        panelPartidos.revalidate();
        panelPartidos.repaint();
    }

    private void actualizarListaPartidos(JPanel panelPartidos, ColaDePartidos colaPartidos) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;

        // Eliminar cualquier componente anterior que esté mostrando la lista de partidos
        for (Component comp : panelPartidos.getComponents()) {
            if (comp instanceof JScrollPane) {
                panelPartidos.remove(comp);
            }
        }

        // Crear el modelo de la tabla basado en los datos actuales de la ColaDePartidos
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Equipo 1", "Equipo 2", "Marcador", "Editar", "Eliminar"}, 0);

        NodoPartido actual = colaPartidos.obtenerNodoFrente();
        while (actual != null) {
            Partido partido = actual.getPartido();
            model.addRow(new Object[]{partido.getEquipo1(), partido.getEquipo2(), partido.getResultado(), "Editar", "Eliminar"});
            actual = actual.getSiguiente();
        }

        JTable tablaPartidos = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Solo las columnas de "Editar" y "Eliminar" son editables
            }
        };

        // Configurar el renderer y editor de botones para la columna de "Editar"
        tablaPartidos.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        tablaPartidos.getColumn("Editar").setCellEditor(new ButtonEditorEditarMarcador(new JCheckBox(), tablaPartidos, colaPartidos));

        // Configurar el renderer y editor de botones para la columna de "Eliminar"
        tablaPartidos.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tablaPartidos.getColumn("Eliminar").setCellEditor(new ButtonEditorEliminarPartido(new JCheckBox(), colaPartidos, panelPartidos, model));

        // Estilos de la tabla
        tablaPartidos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaPartidos.setRowHeight(30);
        tablaPartidos.setGridColor(Color.GRAY);
        tablaPartidos.setBackground(new Color(43, 43, 43));
        tablaPartidos.setForeground(Color.WHITE);
        tablaPartidos.getTableHeader().setBackground(new Color(0, 120, 215));
        tablaPartidos.getTableHeader().setForeground(Color.WHITE);
        tablaPartidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaPartidos.getColumnCount(); i++) {
            tablaPartidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaPartidos);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelPartidos.add(scrollPane, gbc);
        panelPartidos.revalidate();
        panelPartidos.repaint();
    }

    public JPanel crearPanelVerPartidos() {
        JPanel panelVerPartidos = new JPanel(new GridBagLayout());
        panelVerPartidos.setBackground(new Color(60, 63, 65));  // Fondo oscuro del panel
        panelVerPartidos.setBorder(createTitledBorder("Ver Partidos"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta para seleccionar evento
        JLabel lblSeleccionarEvento = createStyledLabel("Seleccionar Evento:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelVerPartidos.add(lblSeleccionarEvento, gbc);

        // ComboBox para seleccionar el evento
        comboBoxVerPartidos = new JComboBox<>();
        comboBoxVerPartidos.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBoxVerPartidos.setBackground(new Color(60, 63, 65));
        comboBoxVerPartidos.setForeground(Color.WHITE);
        comboBoxVerPartidos.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Cargar eventos y verificar si hay eventos disponibles
        cargarEventosEnComboBox(comboBoxVerPartidos);

        gbc.gridx = 1;
        panelVerPartidos.add(comboBoxVerPartidos, gbc);

        // Panel para mostrar los partidos del evento seleccionado
        JPanel panelPartidos = new JPanel(new GridBagLayout());
        panelPartidos.setBackground(new Color(60, 63, 65));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelVerPartidos.add(panelPartidos, gbc);

        // Listener para actualizar el panel cuando se selecciona un evento
        comboBoxVerPartidos.addActionListener(e -> {
            Evento eventoSeleccionado = (Evento) comboBoxVerPartidos.getSelectedItem();
            if (eventoSeleccionado != null && eventoSeleccionado.getID() != -1) {
                actualizarPanelVerPartidos(panelPartidos, eventoSeleccionado);
            } else {
                panelPartidos.removeAll();
                panelPartidos.add(createStyledLabel("Seleccione un evento válido."));
                panelPartidos.revalidate();
                panelPartidos.repaint();
            }
        });

        return panelVerPartidos;
    }

    private void actualizarPanelVerPartidos(JPanel panelPartidos, Evento evento) {
        panelPartidos.removeAll();

        ColaDePartidos colaPartidos = evento.getColapartidos();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título para la lista de partidos
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelPartidos.add(createStyledLabel("Lista de Partidos (Último al Primero):"), gbc);

        // Crear el modelo de la tabla basado en los datos actuales de la ColaDePartidos en orden inverso
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Equipo 1", "Equipo 2", "Marcador"}, 0);

        NodoPartido actual = colaPartidos.obtenerNodoFrente();
        // Crear una pila temporal para invertir el orden de los partidos
        PilaDePartidos pilaTemporal = new PilaDePartidos();
        while (actual != null) {
            pilaTemporal.apilar(actual.getPartido());
            actual = actual.getSiguiente();
        }

        // Desapilar los partidos para agregarlos al modelo en orden inverso
        while (!pilaTemporal.estaVacia()) {
            Partido partido = pilaTemporal.desapilar();
            model.addRow(new Object[]{partido.getEquipo1(), partido.getEquipo2(), partido.getResultado()});
        }

        JTable tablaPartidos = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna columna es editable
            }
        };

        // Estilos de la tabla
        tablaPartidos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaPartidos.setRowHeight(30);
        tablaPartidos.setGridColor(Color.GRAY);
        tablaPartidos.setBackground(new Color(43, 43, 43));
        tablaPartidos.setForeground(Color.WHITE);
        tablaPartidos.getTableHeader().setBackground(new Color(0, 120, 215));
        tablaPartidos.getTableHeader().setForeground(Color.WHITE);
        tablaPartidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaPartidos.getColumnCount(); i++) {
            tablaPartidos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaPartidos);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panelPartidos.add(scrollPane, gbc);

        panelPartidos.revalidate();
        panelPartidos.repaint();
    }

    // Método para guardar los eventos en un archivo binario
    public void guardarEventosEnArchivo(String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(listaEventos);
            System.out.println("Eventos guardados exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar los eventos en el archivo: " + e.getMessage());
        }
    }

    // Método para cargar eventos desde un archivo binario
    public void cargarEventosDesdeArchivo(String nombreArchivo) {
    try (FileInputStream fileIn = new FileInputStream(nombreArchivo);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {

        // Leer el objeto desde el archivo
        ListaDeEventos listaEventosCargada = (ListaDeEventos) in.readObject();
        updateParticipantesTable();

        // Asigna los eventos cargados a la lista actual
        if (listaEventosCargada != null) {
            System.out.println("Eventos cargados correctamente.");
            this.listaEventos = listaEventosCargada;
        } else {
            System.out.println("La lista de eventos cargada es nula.");
        }

    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al cargar el archivo: " + e.getMessage());
        e.printStackTrace();
    }
}

    public void cargarEventosEnComboBox(JComboBox<Evento> comboBox) {
        comboBox.removeAllItems(); // Limpiar elementos anteriores

        Evento[] eventos = listaEventos.obtenerEventos();
        if (eventos.length == 0) {
            comboBox.addItem(new Evento(-1, "No hay eventos creados", "", "", null, null, null));
            comboBox.setEnabled(false);
        } else {
            for (Evento evento : eventos) {
                if (evento.getID() != -1) {
                    comboBox.addItem(evento);
                }
            }
            comboBox.setEnabled(true);
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    public static int generateNumberBasedOnDate() {
        // Obtener la fecha y hora actual en milisegundos
        long currentTimeMillis = System.currentTimeMillis();

        // Convertir el tiempo en milisegundos a un hashcode
        int uniqueInt = Long.valueOf(currentTimeMillis).hashCode();

        // Asegurar que el número sea positivo
        uniqueInt = Math.abs(uniqueInt);

        return uniqueInt;
    }
    
    public ListaDeEventos getListaEventos() {
        return listaEventos;
    }

    public void setEventTableModel(DefaultTableModel eventTableModel) {
        this.eventTableModel = eventTableModel;
    }

    public JTable getEventTable() {
        return eventTable;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getTablaPanel() {
        return tablaPanel;
    }

    public JTable getParticipantesTable() {
        return participantesTable;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public DefaultTableModel getParticipantesTableModel() {
        return participantesTableModel;
    }

    public void setParticipantesTableModel(DefaultTableModel participantesTableModel) {
        this.participantesTableModel = participantesTableModel;
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(JDatePickerImpl datePicker) {
        this.datePicker = datePicker;
    }

    public JPanel getTablaParticipantesPanel() {
        return tablaParticipantesPanel;
    }

    public JComboBox<Evento> getComboBoxEventos() {
        return comboBoxEventos;
    }

    public JComboBox<Evento> getComboBoxVerPartidos() {
        return comboBoxVerPartidos;
    }

}
