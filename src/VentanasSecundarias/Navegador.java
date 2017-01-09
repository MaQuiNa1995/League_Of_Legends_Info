package VentanasSecundarias;
 
import Clases.CrashReporter;
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
import java.util.logging.Logger;
import static javafx.concurrent.Worker.State.FAILED;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 
@SuppressWarnings("serial")
public class Navegador extends JFrame {
 
    private final JFXPanel javaFxPanel = new JFXPanel();
    
    // Componente para cargar paginas web
    private WebEngine engine;
 
    private final JPanel panelTodo = new JPanel(new BorderLayout());
    private final JLabel labelEstado = new JLabel();
 
    private final JButton botonBuscar = new JButton("Buscar");
    private final JTextField direccion = new JTextField();
    private final JProgressBar barraProgreso = new JProgressBar();
 
    public Navegador() {
        iniciarComponentes();
    }
 
    private void iniciarComponentes() {
        crearEscena();
 
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadURL(direccion.getText());
            }
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
 
        getContentPane().add(panelTodo);
 
        setPreferredSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }
 
    private void crearEscena() {
 
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();
            
            engine.titleProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Navegador.this.setTitle(newValue);
                        }
                    });
                }
            });
            
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
            
            engine.locationProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            direccion.setText(newValue);
                        }
                    });
                }
            });
            
            engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            barraProgreso.setValue(newValue.intValue());
                        }
                    });
                }
            });
            
            engine.getLoadWorker().exceptionProperty()
                    .addListener(new ChangeListener<Throwable>() {
                        
                        @Override
                        public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                            if (engine.getLoadWorker().getState() == FAILED) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Logger ARCHIVOLOG = Logger.getLogger("VentanasSecundarias.Navegador");
                                        
                                        ARCHIVOLOG.warning(CrashReporter.fechaActual().concat(" Error al acceder a la pagina del parche "+value.getMessage()));
                                    }
                                });
                            }
                        }
                    });
            
            javaFxPanel.setScene(new Scene(view));
        });
    }
 
    public void loadURL(String url) {
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
            return null;
        }
    }

}