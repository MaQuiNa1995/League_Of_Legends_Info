/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author MaQuiNa
 */
public final class CrashReporter extends JFrame implements ActionListener {

    JPanel panelTodo;

    String claseActual;

    public CrashReporter(String claseActual) {
        this.claseActual = claseActual;
        definirVentana();
        definirPanelTodo();
        definirPanelTodoDentro();
    }

    void definirVentana() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 300);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Crash Reporter");

    }

    void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());

    }

    void definirPanelTodoDentro() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.PAGE_AXIS));

        JPanel descProblemaPanel = new JPanel();
        descProblemaPanel.setLayout(new BoxLayout(descProblemaPanel, BoxLayout.PAGE_AXIS));

        JLabel descProblemaEtiqueta = new JLabel("Descripción Del Problema");
        JTextArea descProblemaText = new JTextArea(300, 200);
        descProblemaText.setLineWrap(true);

        descProblemaPanel.add(descProblemaEtiqueta);
        descProblemaPanel.add(descProblemaText);

        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new BoxLayout(botonPanel, BoxLayout.PAGE_AXIS));

        JButton botonEnviar = new JButton("Enviar");

        botonPanel.add(botonEnviar);

        panelTitulo.add(descProblemaPanel);
        panelTitulo.add(botonPanel);

        panelTodo.add(panelTitulo, BorderLayout.CENTER);

        add(panelTodo);
        setVisible(true);
    }

    static public String fechaActual() {
        Date date = new Date();
        String stringFormatoDate = "d M yyyy hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(stringFormatoDate);
        String dateFormato = dateFormat.format(date);
        return dateFormato;
    }

    // TODO aqui falta excepcion 
    public static void meterCadena(String cadenaMeter, String NombreFichero) {

        try (FileWriter Escribir_Fichero = new FileWriter(Rutas.rutaInstalacion + "/InfoLol/Logs/" + NombreFichero+".txt", false)) {
            Escribir_Fichero.write(cadenaMeter + "\n");
        } catch (IOException error) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equalsIgnoreCase("Enviar")) {

        }
    }
}
