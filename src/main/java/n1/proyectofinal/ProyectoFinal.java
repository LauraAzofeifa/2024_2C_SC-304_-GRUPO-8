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
import javax.swing.SwingUtilities;

/**
 *
 * @author Laura
 */
import javax.swing.*;

public class ProyectoFinal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Sistema de Gestión de Eventos Deportivos");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        initializeComponents(mainFrame);

        mainFrame.setVisible(true);
    }

    private static void initializeComponents(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuEventos = new JMenu("Eventos");
        JMenuItem addEvento = new JMenuItem("Agregar Evento");
        JMenuItem viewEventos = new JMenuItem("Ver Eventos");

        menuEventos.add(addEvento);
        menuEventos.add(viewEventos);
        menuBar.add(menuEventos);

        frame.setJMenuBar(menuBar);

        JPanel cards = new JPanel(new CardLayout());
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Seleccione una opción del menú"));
        JPanel addEventPanel = new JPanel();
        addEventPanel.add(new JLabel("Formulario para agregar eventos"));
        JPanel viewEventsPanel = new JPanel();
        viewEventsPanel.add(new JLabel("Lista de eventos"));

        cards.add(homePanel, "Home");
        cards.add(addEventPanel, "Add Event");
        cards.add(viewEventsPanel, "View Events");

        frame.add(cards, BorderLayout.CENTER);

        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Home");  // Mostrar inicialmente el panel de inicio

        addEvento.addActionListener(e -> cl.show(cards, "Add Event"));
        viewEventos.addActionListener(e -> cl.show(cards, "View Events"));
    }
}
