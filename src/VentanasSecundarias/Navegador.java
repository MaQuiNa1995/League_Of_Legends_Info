package VentanasSecundarias;

import GestionErrores.CrashReporterImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import java.net.MalformedURLException;
import java.net.URL;
import static javafx.concurrent.Worker.State.FAILED;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Navegador extends JFrame {

    static final String CLASEACTUAL = "VentanasSecundarias.Navegador";

    private final JFXPanel javaFxPanel = new JFXPanel();

    // Objeto capaz de manejar una web, carga paginas web
    // y las transforma en una vista de documento tambien es capaz de ejecutar javascrip
    private WebEngine engine;

    private final JLabel labelEstado = new JLabel();

    private final JTextField direccion = new JTextField();
    private final JProgressBar barraProgreso = new JProgressBar();

    public Navegador() {
        iniciarComponentes();
        crearEscena();
    }

    private void iniciarComponentes() {

        JPanel panelTodo = new JPanel(new BorderLayout());
        JButton botonBuscar = new JButton("Buscar");

        ActionListener al = (ActionEvent e) -> {
            cargarURL(direccion.getText());
        };

        botonBuscar.addActionListener(al);
        direccion.addActionListener(al);

        barraProgreso.setPreferredSize(new Dimension(150, 18));
        barraProgreso.setStringPainted(true);

        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        topBar.add(direccion, BorderLayout.CENTER);
        topBar.add(botonBuscar, BorderLayout.EAST);

        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(labelEstado, BorderLayout.CENTER);
        statusBar.add(barraProgreso, BorderLayout.EAST);

        panelTodo.add(topBar, BorderLayout.NORTH);
        panelTodo.add(javaFxPanel, BorderLayout.CENTER);
        panelTodo.add(statusBar, BorderLayout.SOUTH);

        add(panelTodo);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    private void crearEscena() {

        // Hacemos esta linea para definir funciones que se ejecutaran en un futuro
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();

            // Funcion para cambiar el titulo de la pagina
            engine.titleProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String valorAnterior, final String valorDespues) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Navegador.this.setTitle(valorDespues);
                        }
                    });
                }
            });

            // Parte que se encarga de mostrar los links abajo de la ventana a la izquierda
            engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(final WebEvent<String> event) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            labelEstado.setText(event.getData());
                        }
                    });
                }
            });

            // Barra de direcciones
            engine.locationProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String valorAnterior, final String valorDespues) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            direccion.setText(valorDespues);
                        }
                    });
                }
            });

            // barra de carga
            engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number valorAnterior, final Number valorDespues) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            barraProgreso.setValue(valorDespues.intValue());
                        }
                    });
                }
            });

            // Cuando hay una excepcion
            engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {

                @Override
                public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat(" Error al cargar la pagina"), CLASEACTUAL);
                            }
                        });
                    }
                }
            });

            javaFxPanel.setScene(new Scene(view));
        });
    }

    public void cargarURL(String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String tmp = toURL(url);

                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }

                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat(" Error al cargar la pagina URL mal formada"), CLASEACTUAL);
            return null;
        }
    }

}
