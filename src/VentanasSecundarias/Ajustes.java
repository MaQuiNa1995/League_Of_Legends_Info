/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasSecundarias;

import BaseDatos.ArchivoPropiedades;
import GestionErrores.CrashReporterImpl;
import Clases.Rutas;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.TitledBorder;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class Ajustes extends JFrame implements ActionListener {

    static String CLASEACTUAL = "VentanasSecundarias.Ajustes";

    JPanel panelPrincipal, panelTodo;

    JComboBox<String> tamanoTitulo, fuenteTitulo, tamanoCuerpo, fuenteCuerpo;

    JComboBox<String> temas;

    public Ajustes() {

        definirPanelTodo();
        definirPanelTemas();
        definirPanelLetras();

        definirPanelAbajo();
        definirVentana();

        this.add(panelTodo);
        this.pack();
        this.setVisible(true);
    }

    public void definirVentana() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Configuración");
    }

    public void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
    }

    public void definirPanelTemas() {
        JLabel labelTemas;

        UIManager.LookAndFeelInfo[] arrayTemas;

        JPanel panelTemas = new JPanel();

        panelTemas.setBorder(new TitledBorder("Cambiar Tema"));

        labelTemas = new JLabel("Temas Instalados:");
        temas = new JComboBox<>();

        arrayTemas = UIManager.getInstalledLookAndFeels();

        for (UIManager.LookAndFeelInfo objetoSacado : arrayTemas) {
            temas.addItem(objetoSacado.getClassName());
        }

        panelTemas.add(labelTemas);
        panelTemas.add(temas);

        panelPrincipal.add(panelTemas);
    }

    void definirPanelLetras() {

        JPanel titulo, cuerpo;

        JPanel panelLetras = new JPanel();
        panelLetras.setLayout(new BoxLayout(panelLetras, BoxLayout.Y_AXIS));

        panelLetras.setBorder(new TitledBorder("Tamaño y Fuente Letras"));

        JLabel tamanoTituloL, fuenteTituloL, tamanoCuerpoL, fuenteCuerpoL;

        titulo = new JPanel();
        titulo.setLayout(new FlowLayout());

        cuerpo = new JPanel();
        cuerpo.setLayout(new FlowLayout());

        tamanoTituloL = new JLabel("Tamaño Titulo");
        fuenteTituloL = new JLabel("Fuente Titulo");

        tamanoCuerpoL = new JLabel("Tamaño Texto");
        fuenteCuerpoL = new JLabel("Fuente Texto");

        tamanoTitulo = new JComboBox<>();
        fuenteTitulo = new JComboBox<>();
        tamanoCuerpo = new JComboBox<>();
        fuenteCuerpo = new JComboBox<>();

        for (int i = 1; i < 35; i++) {
            tamanoTitulo.addItem(String.valueOf(i));
            tamanoCuerpo.addItem(String.valueOf(i));
        }
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (String fuente : fontNames) {
            fuenteTitulo.addItem(fuente);
            fuenteCuerpo.addItem(fuente);
        }

        cargarProperties();

        titulo.add(tamanoTituloL);
        titulo.add(tamanoTitulo);

        titulo.add(fuenteTituloL);
        titulo.add(fuenteTitulo);

        cuerpo.add(tamanoCuerpoL);
        cuerpo.add(tamanoCuerpo);

        cuerpo.add(fuenteCuerpoL);
        cuerpo.add(fuenteCuerpo);

        panelLetras.add(titulo);
        panelLetras.add(cuerpo);

        panelPrincipal.add(panelLetras);

        panelTodo.add(panelPrincipal, BorderLayout.CENTER);
    }

    public void definirPanelAbajo() {

        JPanel panelAbajo;
        JButton botonAplicar;
        
        panelAbajo = new JPanel();

        botonAplicar = new JButton("Aplicar");
        botonAplicar.addActionListener(this);

        panelAbajo.add(botonAplicar);

        panelTodo.add(panelAbajo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        boolean errorDefTema = false;

        switch (ae.getActionCommand()) {

            case "Aplicar":
                do {
                    try {
                        if (errorDefTema) {
                            String temaDefault = "javax.swing.plaf.metal.MetalLookAndFeel";
                            UIManager.setLookAndFeel(temaDefault);
                            temas.setSelectedItem(temaDefault);
                        } else {
                            UIManager.setLookAndFeel(temas.getSelectedItem().toString());
                        }
                    } catch (ClassNotFoundException
                            | InstantiationException
                            | IllegalAccessException
                            | UnsupportedLookAndFeelException ex) {

                        CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat(" Error al definir el tema " + ex.getMessage()), CLASEACTUAL);
                        errorDefTema = true;
                    }
                } while (errorDefTema == true);
                guardarProperties();

                break;

        }
    }

    public void cargarProperties() {
        Properties propiedades = new ArchivoPropiedades().getProperties();

        temas.setSelectedItem(propiedades.getProperty("temaElegido"));

        tamanoTitulo.setSelectedItem(propiedades.getProperty("tituloTamano"));
        fuenteTitulo.setSelectedItem(propiedades.getProperty("tituloFuente"));

        tamanoCuerpo.setSelectedItem(propiedades.getProperty("textoTamano"));
        fuenteCuerpo.setSelectedItem(propiedades.getProperty("textoFuente"));
    }

    public void guardarProperties() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(Rutas.rutaInstalacion + "/InfoLol/ArchivosPropiedades/conf.properties");
        } catch (org.apache.commons.configuration.ConfigurationException ex) {
            CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat("Error al leer el archivo properties" + ex.getMessage()), CLASEACTUAL);
            CrashReporterImpl cr = new CrashReporterImpl(CLASEACTUAL);
        }

        String temaElegido = temas.getSelectedItem().toString();

        config.setProperty("temaElegido", temaElegido);

        config.setProperty("textoFuente", fuenteCuerpo.getSelectedItem().toString());
        config.setProperty("textoTamano", tamanoCuerpo.getSelectedItem().toString());

        config.setProperty("tituloFuente", fuenteTitulo.getSelectedItem().toString());
        config.setProperty("tituloTamano", tamanoTitulo.getSelectedItem().toString());

        try {
            config.save();
        } catch (org.apache.commons.configuration.ConfigurationException ex) {
            CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat("Error al guardar el archivo properties" + ex.getMessage()), CLASEACTUAL);
            CrashReporterImpl cr = new CrashReporterImpl(CLASEACTUAL);
        }

    }
}
