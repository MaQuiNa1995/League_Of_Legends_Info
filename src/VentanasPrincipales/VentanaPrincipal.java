package VentanasPrincipales;

import BaseDatos.ArchivoPropiedades;
import Clases.CrashReporter;
import VentanasSecundarias.Ajustes;
import VentanasSecundarias.HistoriasCampeones;
import VentanasSecundarias.InfoCampeones;
import VentanasSecundarias.Navegador;
import VentanasSecundarias.SkinsCampeones;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class VentanaPrincipal extends JFrame implements ActionListener {
    
        static String claseActual="VentanasPrincipales.VentanasPrincipales";
    
    static final Logger ARCHIVOLOG = Logger.getLogger(claseActual);

    JPanel menuPrincipal, menuExterior;

    JButton arrayBotones[] = new JButton[5];

    JButton consultarCampeones, consultarLores,
            consultarSplashArt, consultarParcheActual, ajustes;

    String[] arrayNombreBotones = {
        "Ver Informacion De Campeones",
        "Leer Historias De Los Campeones",
        "Ver Splash Arts De Los Campeones",
        "Consultar Parche Actual",
        "Configuración"
    };

    String[] arrayToolTipText = {
        "Te Muestra Informacion De Las Habilidades De Los Campeones y Estadisticas De Estos ",
        "Muestra Una Breve Historia De Los Campeones",
        "Te Muestra Las Diferentes Skins Que Tienen Los Campeones y Sus Costes",
        "Muestra Informacion Sobre El Parche Actual",
        "Aquí Podras Modificar El Estilo De La Aplicación Así Como Ajustes Varios"
    };

    public VentanaPrincipal() {

        definirVentana();
        establecerTema();
        definirMenuPrincipal();
    }

    void definirVentana() {
        setSize(500, 500);
        setLocation(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // esta parte no funciona
        //setIconImage(new ImageIcon(getClass().getResource("/Imagen/barco.ico")).getImage());
        setTitle("League Of Legends Info");
    }

    public void definirMenuPrincipal() {
        menuPrincipal = new JPanel();
        menuPrincipal.setLayout(new GridLayout(5, 1, 2, 2));

        for (int i = 0; i < arrayBotones.length; i++) {
            arrayBotones[i] = new JButton(arrayNombreBotones[i]);
            arrayBotones[i].addActionListener(this);
            arrayBotones[i].setToolTipText(arrayToolTipText[i]);
            menuPrincipal.add(arrayBotones[i]);
        }
        add(menuPrincipal);

        setVisible(true);

    }

    void establecerTema() {
        Properties propiedades = new ArchivoPropiedades().getProperties();

        String temaElegido = propiedades.getProperty("temaElegido");

        boolean errorDefTema = false;

        do {
            try {
                if (errorDefTema) {
                    String temaDefault = "javax.swing.plaf.metal.MetalLookAndFeel";
                    UIManager.setLookAndFeel(temaDefault);
                } else {
                    UIManager.setLookAndFeel(temaElegido);
                }
            } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException ex) {
                
                CrashReporter.meterCadena(CrashReporter.fechaActual().concat(" Error al definir el tema "+ex.getMessage()), claseActual);
                CrashReporter cr = new CrashReporter(claseActual);
                
                errorDefTema = true;
            }
        } while (errorDefTema == true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {

            case "Ver Informacion De Campeones":
                InfoCampeones infoChamp = new InfoCampeones();
                break;

            case "Leer Historias De Los Campeones":
                HistoriasCampeones loreChamp = new HistoriasCampeones();
                break;

            case "Ver Splash Arts De Los Campeones":
                SkinsCampeones skinChamp = new SkinsCampeones();
                break;

            case "Consultar Parche Actual":
                SwingUtilities.invokeLater(() -> {
                    Navegador browser = new Navegador();
                    browser.setVisible(true);

                    Properties propiedades = new ArchivoPropiedades().getProperties();
                    String temporada = propiedades.getProperty("temporada");
                    String version = propiedades.getProperty("version");

                    browser.loadURL("http://euw.leagueoflegends.com/es/news/game-updates/patch/notas-de-la-version-" + temporada + version);
                });
                break;

            case "Configuración":
                Ajustes ajustes1 = new Ajustes();
                break;
        }
    }
}
