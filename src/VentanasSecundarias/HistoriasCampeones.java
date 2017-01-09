/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasSecundarias;

import BaseDatos.ArchivoPropiedades;
import BaseDatos.Conector;
import Clases.Rutas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author MaQui
 */
@SuppressWarnings("serial")
public final class HistoriasCampeones extends JFrame implements ActionListener {

    JPanel panelTodo, panelArriba, buscarCampeonPanel, panelImagen,
            panelAbajo, infoPanel, panelIzquierda;

    JLabel imagen, nombre;

    Conector con = new Conector();

    Properties propiedades = new ArchivoPropiedades().getProperties();

    boolean error;

    String campeonABuscar, inicial, despues;

    JTextField buscarCampeon;

    JTextArea mostrarCampeones;

    JComboBox<String> nombreCampeonCombo;

    ArrayList<String> arrayCampeones;

    JButton botonBuscar;

    public HistoriasCampeones() {
        definirVentana();
        definirPanelTodo();
        definirPanelArriba();
        definirPanelDerecha();
        definirPanelIzquierda();
    }

    void definirVentana() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Historia De Los Campeones");

    }

    void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());

    }

    void definirPanelArriba() {
        panelArriba = new JPanel();
        panelArriba.setLayout(new BoxLayout(panelArriba, BoxLayout.PAGE_AXIS));

        //buscar campeon
        buscarCampeonPanel = new JPanel();

        nombreCampeonCombo = new JComboBox<>();

        arrayCampeones = new ArrayList<>();
        arrayCampeones = con.nombresCampeones();

        for (String objetoSacado : arrayCampeones) {
            nombreCampeonCombo.addItem(objetoSacado);
        }

        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(this);

        buscarCampeonPanel.add(nombreCampeonCombo);
        buscarCampeonPanel.add(botonBuscar);

        infoPanel = new JPanel();

        Font fuente2 = new FontUIResource("Algerian", Font.BOLD, 25);

        nombre = new JLabel("");
        nombre.setFont(fuente2);

        // cambiando aqui
        panelArriba.add(buscarCampeonPanel);

        panelArriba.add(infoPanel);

        panelTodo.add(panelArriba, BorderLayout.NORTH);

    }

    public void definirPanelIzquierda() {
        panelIzquierda = new JPanel();

        panelIzquierda.add(Box.createRigidArea(new Dimension(10, 20)));

        panelTodo.add(panelIzquierda, BorderLayout.WEST);

    }

    public void definirPanelDerecha() {
        panelAbajo = new JPanel();
        panelAbajo.setLayout(new BoxLayout(panelAbajo, BoxLayout.LINE_AXIS));

        Font fuente = new FontUIResource("Serif", Font.PLAIN, 17);

        panelImagen = new JPanel();
        imagen = new JLabel();

        panelImagen.add(Box.createRigidArea(new Dimension(10, 20)));
        panelImagen.add(imagen);
        panelImagen.add(Box.createRigidArea(new Dimension(10, 20)));

        panelTodo.add(panelImagen, BorderLayout.EAST);

        infoPanel.add(nombre);

        JPanel lorePanel = new JPanel();

        mostrarCampeones = new JTextArea(20, 85);

        mostrarCampeones.setFont(fuente);
        mostrarCampeones.setEditable(true);
        mostrarCampeones.setVisible(false);
        mostrarCampeones.setLineWrap(true);

        lorePanel.add(mostrarCampeones);
        panelAbajo.add(lorePanel);

        panelTodo.add(panelAbajo, BorderLayout.CENTER);

        add(panelTodo);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String directorioRecursos,
                rutaCampeones;

        directorioRecursos = Rutas.rutaInstalacion;
        rutaCampeones = directorioRecursos.concat("/InfoLol/Imagenes/Campeones/");

        campeonABuscar = nombreCampeonCombo.getSelectedItem().toString();

        switch (ae.getActionCommand()) {

            case "Buscar":

                mostrarCampeones.setText("");
                imagen.setIcon(new ImageIcon(""));

                imagen.setIcon(new ImageIcon(rutaCampeones + campeonABuscar + ".png"));
                mostrarCampeones.setVisible(true);
                mostrarCampeones.append(con.mostrarLore(campeonABuscar));
                nombre.setText(con.mostrarNombreMote(campeonABuscar));

        }

    }
}
