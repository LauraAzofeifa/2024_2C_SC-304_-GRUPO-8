/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package n1.proyectofinal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Laura
 */
import javax.swing.*;

public class ProyectoFinal {

    private final ListaDeEventos listaEventos = new ListaDeEventos();
    private InterfazManager eventoManager ;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProyectoFinal proyectoFinal = new ProyectoFinal();
            proyectoFinal.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Sistema de Gestión de Eventos Deportivos");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // No cerrar directamente
        mainFrame.setSize(800, 800);
        mainFrame.setLocationRelativeTo(null);

        initializeComponents(mainFrame, listaEventos);

        mainFrame.setVisible(true);

        // Agregar WindowListener para confirmar la salida
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmarSalida(mainFrame);
            }
        });
    }

    private void initializeComponents(JFrame frame, ListaDeEventos listaEventos) {

        eventoManager = new InterfazManager(listaEventos);
        JMenuBar menuBar = new JMenuBar();
        
        try {
            eventoManager.cargarEventosDesdeArchivo("eventos.dat");
        } catch (Exception e) {
        } finally {
        }

        // Menú "Eventos"
        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem addEvento = new JMenuItem("Agregar Evento");
        JMenuItem viewEventos = new JMenuItem("Ver Eventos");
        menuEventos.add(addEvento);
        menuEventos.add(viewEventos);
        menuBar.add(menuEventos);

        // Menú "Participantes"
        JMenu menuParticipantes = new JMenu("Participantes");
        JMenuItem viewParticipantes = new JMenuItem("Ver Participantes");
        menuParticipantes.add(viewParticipantes);
        menuBar.add(menuParticipantes);

        // Programación de Eventos
        JMenu menuProgEventos = new JMenu("Programación de Eventos");
        JMenuItem addProgEventos = new JMenuItem("Programación de Partido");
        menuProgEventos.add(addProgEventos);
        menuBar.add(menuProgEventos);

        // Menú Navegación Consultas
        JMenu menuConsultas = new JMenu("Navegación y Consultas");
        JMenuItem viewConParticipantes = new JMenuItem("Consulta Participantes");
        JMenuItem viewConEventos = new JMenuItem("Consulta Eventos");
        JMenuItem viewConResultados = new JMenuItem("Consulta Resultados");
        menuConsultas.add(viewConParticipantes);
        menuConsultas.add(viewConEventos);
        menuConsultas.add(viewConResultados);
        menuBar.add(menuConsultas);

        frame.setJMenuBar(menuBar);
        JPanel cards = new JPanel(new CardLayout());
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Seleccione una opción del menú"));

        // Panels para Eventos
        JPanel addEventPanel = eventoManager.getMainPanel();
        JPanel viewEventsPanel = eventoManager.getTablaPanel();

        // Panels para Participantes
        JPanel viewParticipantesPanel = eventoManager.getTablaParticipantesPanel();

        // Panels Navegación Consultas
        JPanel viewProgEventosPanel = eventoManager.crearPanelGestionPartidos();

        // Panels para Consultas
        JPanel viewConParticipantesPanel = eventoManager.crearPanelBusquedaParticipante();
        JPanel viewConEventosPanel = eventoManager.crearPanelBusquedaEvento();
        JPanel viewConResultadosPanel = eventoManager.crearPanelVerPartidos();

        cards.add(homePanel, "Home");
        cards.add(addEventPanel, "Add Event");
        cards.add(viewEventsPanel, "View Events");
        cards.add(viewParticipantesPanel, "View Participantes");
        cards.add(viewProgEventosPanel, "View Programacion Partidos");
        cards.add(viewConParticipantesPanel, "View Consultas Participantes");
        cards.add(viewConEventosPanel, "View Consultas Eventos");
        cards.add(viewConResultadosPanel, "View Consultas Resultados");

        frame.add(cards, BorderLayout.CENTER);

        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Home");  // Mostrar inicialmente el panel de inicio

        // Acciones para el menú de Eventos
        addEvento.addActionListener(e -> cl.show(cards, "Add Event"));
        viewEventos.addActionListener(e -> {
            eventoManager.updateEventTable();
            cl.show(cards, "View Events");
        });

        // Acciones para el menú de Participantes
        viewParticipantes.addActionListener(e -> cl.show(cards, "View Participantes"));

        // Acciones para el menú de Programación de Eventos
        addProgEventos.addActionListener(e -> {
            eventoManager.cargarEventosEnComboBox(eventoManager.getComboBoxEventos());
            cl.show(cards, "View Programacion Partidos");
        });

        // Acciones para el menú de Navegación y Consultas
        viewConParticipantes.addActionListener(e -> cl.show(cards, "View Consultas Participantes"));
        viewConEventos.addActionListener(e -> {
            cl.show(cards, "View Consultas Eventos");
        });
        viewConResultados.addActionListener(e -> {
            eventoManager.cargarEventosEnComboBox(eventoManager.getComboBoxVerPartidos());
            cl.show(cards, "View Consultas Resultados");
        });
        
        eventoManager.updateParticipantesTable();
    }

    private void confirmarSalida(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, 
                "¿Estás seguro de que quieres salir? Se guardarán todos los eventos.",
                "Confirmar Salida", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
        
        if (option == JOptionPane.YES_OPTION) {
            // Guardar los eventos antes de salir
            try {
                eventoManager.guardarEventosEnArchivo("eventos.dat");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, 
                        "Error al guardar los eventos: " + e.getMessage(),
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
            // Salir de la aplicación
            System.exit(0);
        }
    }
}
