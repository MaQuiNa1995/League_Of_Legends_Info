package VentanasSecundarias;

import BaseDatos.ArchivoPropiedades;
import BaseDatos.Conector;
import Clases.Habilidad;
import Clases.Rutas;
import Clases.Pasiva;
import Clases.Stats;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

public final class InfoCampeones extends JFrame implements MouseListener, ActionListener {

    Conector con = new Conector();

    Font algerian = new FontUIResource("Arial", Font.BOLD, 15);
    Font algerian2 = new FontUIResource("Arial", Font.BOLD, 24);

    Font cuerpo;
    Font titulo;

    Border border = LineBorder.createGrayLineBorder();

    List<Habilidad> arrayListHabilidades = new ArrayList<>();
    List<Pasiva> arrayListPasivas = new ArrayList<>();
    List<String> arrayListNombres;

    JPanel panelTodo, panelTitulo, panelImagenes, panelImagenes2, panelStats, panelHabilidades, panelPasivas;

    // --- Panel Imagenes 2 ---
    JLabel imagenCampeon, costeIPCampeon, rolCampeon;
    
    String nombreDelCampeon;

    JLabel vidaImagen,
            regvidaImagen,
            manaImagen,
            regmanaImagen,
            ataqueImagen,
            veloataqueImagen,
            armaduraImagen,
            resismagicaImagen,
            velomovImagen;

    JLabel vida,
            regvida,
            mana,
            regmana,
            ataque,
            veloataque,
            armadura,
            resismagica,
            velomov;

    JPanel vidaPanel,
            regvidaPanel,
            manaPanel,
            regmanaPanel,
            ataquePanel,
            veloataquePanel,
            armaduraPanel,
            resismagicaPanel,
            velomovPanel;

    // --- Panel Titulo ---
    JLabel nombreCampeonLabel, moteCampeon;
    JComboBox<String> nombreCampeonCombo;
    JButton buscar;

    String rutaCampeones = Rutas.rutaInstalacion+"/InfoLol/Imagenes/Campeones/";

    // --- Panel Imagenes ---
    JLabel fotoP, fotoQ, fotoW, fotoE, fotoR;
    JPanel pasiva, teclaQ, teclaW, teclaE, teclaR;

    String rutaPasivas = Rutas.rutaInstalacion+"/InfoLol/Imagenes/Pasivas/";
    String rutaQ = Rutas.rutaInstalacion+"/InfoLol/Imagenes/Q/";
    String rutaW = Rutas.rutaInstalacion+"/InfoLol/Imagenes/W/";
    String rutaE = Rutas.rutaInstalacion+"/InfoLol/Imagenes/E/";
    String rutaR = Rutas.rutaInstalacion+"/InfoLol/Imagenes/R/";

    // --- Panel Habilidades ---
    JLabel nombreHabilidad, descripCorta, descripLarga, coste, enfriamiento;
    JTextArea nombreHabilidad2, descripCorta2, descripLarga2, coste2, enfriamiento2;

    JPanel habilidadPanelin, descriCortaPanelin, descriLargaPanelin, costePanelin, enfriamientoPanelin;

    // --- Panel Pasivas ---
    JLabel nombrePasiva, descripPasiva, enfriamientoPasiva;
    JTextArea nombrePasiva2, descripPasiva2, enfriamientoPasiva2;

    JPanel nombrePasivaPanelin, descripPasivaPanelin, enfriamientoPasivaPanelin;

    // --- Panel ---
    public InfoCampeones() {
        inicializarFuentes();
        definirVentana();
        definirPanelTodo();
        definirPanelTitulo();
        definirPanelIzquierda();
        definirPanelDerecha();
        definirPanelHabilidades();

        add(panelTodo);
        setVisible(true);
    }

    void definirVentana() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Informacion De Los Campeones");
    }

    public void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());

    }

    public void definirPanelTitulo() {
        panelTitulo = new JPanel(new FlowLayout());
        nombreCampeonLabel = new JLabel("Nombre Campeón:");
        nombreCampeonLabel.setFont(algerian2);
        nombreCampeonCombo = new JComboBox<>();

        arrayListNombres = new ArrayList<>();
        arrayListNombres = con.nombresCampeones();

        arrayListNombres.stream().forEach((objetoSacado) -> {
            nombreCampeonCombo.addItem(objetoSacado);
        });

        buscar = new JButton("Buscar");
        buscar.addActionListener(this);

        moteCampeon = new JLabel();
        moteCampeon.setFont(algerian2);

        panelTitulo.add(nombreCampeonLabel);
        panelTitulo.add(nombreCampeonCombo);
        panelTitulo.add(buscar);

        panelTitulo.add(moteCampeon);

        panelTodo.add(panelTitulo, BorderLayout.NORTH);
    }

    public void definirPanelIzquierda() {

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.X_AXIS));
        panelIzquierda.add(Box.createRigidArea(new Dimension(10, 20)));

        panelImagenes = new JPanel(new GridLayout(5, 1));

        pasiva = new JPanel();
        teclaQ = new JPanel();
        teclaW = new JPanel();
        teclaE = new JPanel();
        teclaR = new JPanel();

        fotoP = new JLabel();
        fotoP.addMouseListener(this);

        fotoQ = new JLabel();
        fotoQ.addMouseListener(this);

        fotoW = new JLabel();
        fotoW.addMouseListener(this);

        fotoE = new JLabel();
        fotoE.addMouseListener(this);

        fotoR = new JLabel();
        fotoR.addMouseListener(this);

        pasiva.add(fotoP);
        teclaQ.add(fotoQ);
        teclaW.add(fotoW);
        teclaE.add(fotoE);
        teclaR.add(fotoR);

        panelImagenes.add(pasiva);
        panelImagenes.add(teclaQ);
        panelImagenes.add(teclaW);
        panelImagenes.add(teclaE);
        panelImagenes.add(teclaR);

        panelIzquierda.add(panelImagenes);
        panelIzquierda.add(Box.createRigidArea(new Dimension(10, 20)));

        panelTodo.add(panelIzquierda, BorderLayout.WEST);

    }

    public void definirPanelDerecha() {

        JPanel panelDerecha = new JPanel();
        panelDerecha.add(Box.createRigidArea(new Dimension(10, 20)));

        panelImagenes2 = new JPanel();
        panelImagenes2.setLayout(new BoxLayout(panelImagenes2, BoxLayout.Y_AXIS));
        panelStats = new JPanel();
        panelStats.setLayout(new BoxLayout(panelStats, BoxLayout.Y_AXIS));

        imagenCampeon = new JLabel();

        costeIPCampeon = new JLabel();
        costeIPCampeon.setFont(algerian2);

        rolCampeon = new JLabel();
        rolCampeon.setFont(titulo);

        FlowLayout fl = new FlowLayout((int) LEFT_ALIGNMENT);

        vidaPanel = new JPanel(fl);
        regvidaPanel = new JPanel(fl);
        manaPanel = new JPanel(fl);
        regmanaPanel = new JPanel(fl);
        ataquePanel = new JPanel(fl);
        veloataquePanel = new JPanel(fl);
        armaduraPanel = new JPanel(fl);
        resismagicaPanel = new JPanel(fl);
        velomovPanel = new JPanel(fl);

        vida = new JLabel();
        regvida = new JLabel();
        mana = new JLabel();
        regmana = new JLabel();
        ataque = new JLabel();
        veloataque = new JLabel();
        armadura = new JLabel();
        resismagica = new JLabel();
        velomov = new JLabel();
        
        String directorioInstalacion= Rutas.rutaInstalacion;

        String rutaDibujos = directorioInstalacion+"/InfoLol/Imagenes/DibujosStat/";

        vidaImagen = new JLabel();
        vidaImagen.setIcon(new ImageIcon(rutaDibujos + "Vida.jpg"));
        regvidaImagen = new JLabel();
        regvidaImagen.setIcon(new ImageIcon(rutaDibujos + "RegVida.jpg"));
        manaImagen = new JLabel();
        manaImagen.setIcon(new ImageIcon(rutaDibujos + "Mana.jpg"));
        regmanaImagen = new JLabel();
        regmanaImagen.setIcon(new ImageIcon(rutaDibujos + "RegMana.jpg"));
        ataqueImagen = new JLabel();
        ataqueImagen.setIcon(new ImageIcon(rutaDibujos + "Ataque.jpg"));
        veloataqueImagen = new JLabel();
        veloataqueImagen.setIcon(new ImageIcon(rutaDibujos + "VeloAtaque.jpg"));
        armaduraImagen = new JLabel();
        armaduraImagen.setIcon(new ImageIcon(rutaDibujos + "Armadura.jpg"));
        resismagicaImagen = new JLabel();
        resismagicaImagen.setIcon(new ImageIcon(rutaDibujos + "ResisMagica.jpg"));
        velomovImagen = new JLabel();
        velomovImagen.setIcon(new ImageIcon(rutaDibujos + "VeloMov.jpg"));

        vidaPanel.add(vidaImagen);
        vidaPanel.add(vida);

        regvidaPanel.add(regvidaImagen);
        regvidaPanel.add(regvida);

        manaPanel.add(manaImagen);
        manaPanel.add(mana);

        regmanaPanel.add(regmanaImagen);
        regmanaPanel.add(regmana);

        ataquePanel.add(ataqueImagen);
        ataquePanel.add(ataque);

        veloataquePanel.add(veloataqueImagen);
        veloataquePanel.add(veloataque);

        armaduraPanel.add(armaduraImagen);
        armaduraPanel.add(armadura);

        resismagicaPanel.add(resismagicaImagen);
        resismagicaPanel.add(resismagica);

        velomovPanel.add(velomovImagen);
        velomovPanel.add(velomov);

        panelImagenes2.add(imagenCampeon);
        panelImagenes2.add(costeIPCampeon);
        panelImagenes2.add(rolCampeon);

        panelStats.add(vidaPanel);
        vidaPanel.setVisible(false);
        panelStats.add(regvidaPanel);
        regvidaPanel.setVisible(false);
        panelStats.add(manaPanel);
        manaPanel.setVisible(false);
        panelStats.add(regmanaPanel);
        regmanaPanel.setVisible(false);
        panelStats.add(ataquePanel);
        ataquePanel.setVisible(false);
        panelStats.add(veloataquePanel);
        veloataquePanel.setVisible(false);
        panelStats.add(armaduraPanel);
        armaduraPanel.setVisible(false);
        panelStats.add(resismagicaPanel);
        resismagicaPanel.setVisible(false);
        panelStats.add(velomovPanel);
        velomovPanel.setVisible(false);

        panelStats.setVisible(false);

        panelStats.setBorder(new TitledBorder("Estadísticas Del Personaje"));
        panelImagenes2.add(panelStats);

        panelDerecha.add(panelImagenes2);
        panelDerecha.add(Box.createRigidArea(new Dimension(10, 20)));

        panelTodo.add(panelDerecha, BorderLayout.EAST);
    }

    public void definirPanelHabilidades() {

        panelHabilidades = new JPanel();
        panelHabilidades.setLayout(new BoxLayout(panelHabilidades, BoxLayout.Y_AXIS));

        // Nombre
        nombreHabilidad = new JLabel("Nombre De Habilidad: ");
        nombreHabilidad.setFont(titulo);
        nombreHabilidad2 = new JTextArea("");
        nombreHabilidad2.setFont(cuerpo);
        nombreHabilidad2.setLineWrap(true);
        nombreHabilidad2.setMaximumSize(new Dimension(1172, 30));

        habilidadPanelin = new JPanel();
        habilidadPanelin.setLayout(new BoxLayout(habilidadPanelin, BoxLayout.Y_AXIS));

        habilidadPanelin.add(nombreHabilidad);
        habilidadPanelin.add(nombreHabilidad2);

        panelHabilidades.add(habilidadPanelin);

        // descri
        descripCorta = new JLabel("Descripcion Corta:");
        descripCorta.setFont(titulo);
        descripCorta2 = new JTextArea("");
        descripCorta2.setFont(cuerpo);
        descripCorta2.setLineWrap(true);
        descripCorta2.setMaximumSize(new Dimension(1172, 80));

        //descripCorta2.setMaximumSize(new Dimension(50,50));
        descriCortaPanelin = new JPanel();
        descriCortaPanelin.setLayout(new BoxLayout(descriCortaPanelin, BoxLayout.Y_AXIS));

        descriCortaPanelin.add(descripCorta);
        descriCortaPanelin.add(descripCorta2);

        panelHabilidades.add(descriCortaPanelin);

        descripLarga = new JLabel("Descripcion Larga:");
        descripLarga.setFont(titulo);
        descripLarga2 = new JTextArea("");
        descripLarga2.setFont(cuerpo);
        descripLarga2.setLineWrap(true);
        descripLarga2.setMaximumSize(new Dimension(1172, 120));

        descriLargaPanelin = new JPanel();
        descriLargaPanelin.setLayout(new BoxLayout(descriLargaPanelin, BoxLayout.Y_AXIS));
        descriLargaPanelin.add(descripLarga);
        descriLargaPanelin.add(descripLarga2);

        panelHabilidades.add(descriLargaPanelin);

        coste = new JLabel("Coste:");
        coste.setFont(titulo);
        coste2 = new JTextArea("");
        coste2.setFont(cuerpo);
        coste2.setLineWrap(true);
        coste2.setMaximumSize(new Dimension(1172, 35));

        costePanelin = new JPanel();
        costePanelin.setLayout(new BoxLayout(costePanelin, BoxLayout.Y_AXIS));

        costePanelin.add(coste);
        costePanelin.add(coste2);

        panelHabilidades.add(costePanelin);

        // Enfriamiento
        enfriamiento = new JLabel("Enfriamiento:");
        enfriamiento.setFont(titulo);
        enfriamiento2 = new JTextArea("");
        enfriamiento2.setFont(cuerpo);
        enfriamiento2.setLineWrap(true);
        enfriamiento2.setMaximumSize(new Dimension(1172, 30));

        enfriamientoPanelin = new JPanel();
        enfriamientoPanelin.setLayout(new BoxLayout(enfriamientoPanelin, BoxLayout.PAGE_AXIS));

        enfriamientoPanelin.add(enfriamiento);
        enfriamientoPanelin.add(enfriamiento2);

        // Setear Visibilidad
        descripLarga.setVisible(false);
        coste.setVisible(false);
        descripLarga2.setVisible(false);
        coste2.setVisible(false);

        // Poner Bordes
        nombreHabilidad2.setBorder(border);
        descripCorta2.setBorder(border);
        descripLarga2.setBorder(border);
        coste2.setBorder(border);
        enfriamiento2.setBorder(border);

        // Setear Editabilidad
        nombreHabilidad2.setEditable(false);
        descripCorta2.setEditable(false);
        descripLarga2.setEditable(false);
        coste2.setEditable(false);
        coste2.setRows(1);
        enfriamiento2.setEditable(false);

        panelHabilidades.add(enfriamientoPanelin);

        //panelTodo.add(panelHabilidades, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {

        fotoP.setBorder(null);
        fotoQ.setBorder(null);
        fotoW.setBorder(null);
        fotoE.setBorder(null);
        fotoR.setBorder(null);

        panelTodo.add(panelHabilidades, BorderLayout.CENTER);

        nombreHabilidad.setText("Nombre Habilidad");
        descripCorta.setText("Descripción Corta");
        enfriamiento.setText("Enfriamiento");

//        nombreHabilidad.setVisible(true);
//        nombreHabilidad2.setVisible(true);
        descripCorta.setVisible(true);
        descripCorta2.setVisible(true);
        descripLarga.setVisible(true);
        coste.setVisible(true);
        descripLarga2.setVisible(true);
        coste2.setVisible(true);
        enfriamiento.setVisible(true);
        enfriamiento2.setVisible(true);

        if (me.getSource().equals(fotoP)) {

            fotoP.setBorder(new BevelBorder(BevelBorder.LOWERED));

            arrayListPasivas.stream().map((objetoSacado) -> {
                nombreHabilidad.setText("Nombre Pasiva");
                nombreHabilidad.setVisible(true);
                nombreHabilidad2.setVisible(true);
                descripCorta.setText("Descripción");
                descripLarga.setVisible(false);
                coste.setVisible(false);
                descripLarga2.setVisible(false);
                coste2.setVisible(false);
                nombreHabilidad2.setText(objetoSacado.getNombre());
                descripCorta2.setText(objetoSacado.getDescripcion());
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                if (objetoSacado.getEnfriamiento() == null) {
                    enfriamiento.setVisible(false);
                    enfriamiento2.setVisible(false);
                } else {
                    enfriamiento2.setText(objetoSacado.getEnfriamiento() + "");
                    enfriamiento.setVisible(true);
                    enfriamiento2.setVisible(true);
                }
            });

        }

        if (me.getSource().equals(fotoQ)) {

            fotoQ.setBorder(new BevelBorder(BevelBorder.LOWERED));

            arrayListHabilidades.stream().filter((objetoSacado) -> (objetoSacado.getTecla().equalsIgnoreCase("q"))).map((objetoSacado) -> {
                nombreHabilidad2.setText(objetoSacado.getNombre());
                descripCorta2.setText(objetoSacado.getDescripcioncorta());
                descripLarga2.setText(objetoSacado.getDescripcionlarga());
                coste2.setText(objetoSacado.getCoste());
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                enfriamiento2.setText(objetoSacado.getEnfriamiento());
            });
        }

        if (me.getSource().equals(fotoW)) {

            fotoW.setBorder(new BevelBorder(BevelBorder.LOWERED));

            arrayListHabilidades.stream().filter((objetoSacado) -> (objetoSacado.getTecla().equalsIgnoreCase("w"))).map((objetoSacado) -> {
                nombreHabilidad2.setText(objetoSacado.getNombre());
                descripCorta2.setText(objetoSacado.getDescripcioncorta());
                descripLarga2.setText(objetoSacado.getDescripcionlarga());
                coste2.setText(objetoSacado.getCoste());
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                enfriamiento2.setText(objetoSacado.getEnfriamiento());
            });
        }

        if (me.getSource().equals(fotoE)) {
            fotoE.setBorder(new BevelBorder(BevelBorder.LOWERED));

            arrayListHabilidades.stream().filter((objetoSacado) -> (objetoSacado.getTecla().equalsIgnoreCase("e"))).map((objetoSacado) -> {
                nombreHabilidad2.setText(objetoSacado.getNombre());
                descripCorta2.setText(objetoSacado.getDescripcioncorta());
                descripLarga2.setText(objetoSacado.getDescripcionlarga());
                coste2.setText(objetoSacado.getCoste());
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                enfriamiento2.setText(objetoSacado.getEnfriamiento());
            });
        }

        if (me.getSource().equals(fotoR)) {

            fotoR.setBorder(new BevelBorder(BevelBorder.LOWERED));

            arrayListHabilidades.stream().filter((objetoSacado) -> (objetoSacado.getTecla().equalsIgnoreCase("r"))).map((objetoSacado) -> {
                nombreHabilidad2.setText(objetoSacado.getNombre());
                descripCorta2.setText(objetoSacado.getDescripcioncorta());
                descripLarga2.setText(objetoSacado.getDescripcionlarga());
                coste2.setText(objetoSacado.getCoste());
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                enfriamiento2.setText(objetoSacado.getEnfriamiento());
            });

        }

    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String campeonBuscar = nombreCampeonCombo.getSelectedItem().toString();
        switch (ae.getActionCommand()) {
            case "Buscar":
                panelStats.setVisible(true);

                nombreHabilidad.setVisible(false);
                nombreHabilidad2.setVisible(false);
                descripCorta.setVisible(false);
                descripCorta2.setVisible(false);

                descripLarga.setVisible(false);
                descripLarga2.setVisible(false);
                coste.setVisible(false);
                coste2.setVisible(false);
                enfriamiento.setVisible(false);
                enfriamiento2.setVisible(false);

                pasiva.setBorder(new TitledBorder("Pasiva"));
                teclaQ.setBorder(new TitledBorder("Q"));
                teclaW.setBorder(new TitledBorder("W"));
                teclaE.setBorder(new TitledBorder("E"));
                teclaR.setBorder(new TitledBorder("R"));

                nombreHabilidad.setText("Nombre Pasiva");
                descripCorta.setText("Descripción");

                moteCampeon.setText(con.mostrarNombreMote(campeonBuscar));
                arrayListPasivas = con.mostrarPasiva(campeonBuscar);
                arrayListHabilidades = con.mostrarHabilidad(campeonBuscar);

                costeIPCampeon.setText("IP:" + con.mostrarCosteIpCampeon(campeonBuscar));
                rolCampeon.setText("Rol:" + con.mostrarRolCampeon(campeonBuscar));

//                try {
                Stats stat = con.mostrarStats(campeonBuscar);

                vida.setText(stat.getVida());
                regvida.setText(stat.getRegvida());

                mana.setText(stat.getMana());
                regmana.setText(stat.getRegmana());

                ataque.setText(stat.getAtaque());
                veloataque.setText(stat.getVeloataque());
                armadura.setText(stat.getArmadura());
                resismagica.setText(stat.getResismagica());
                velomov.setText(stat.getVelomov());
                vidaPanel.setVisible(true);
                regvidaPanel.setVisible(true);
                manaPanel.setVisible(true);
                regmanaPanel.setVisible(true);
                ataquePanel.setVisible(true);
                veloataquePanel.setVisible(true);
                armaduraPanel.setVisible(true);
                resismagicaPanel.setVisible(true);
                velomovPanel.setVisible(true);

                if (stat.getMana().equalsIgnoreCase("0")) {
                    manaPanel.setVisible(false);
                    regmanaPanel.setVisible(false);
                }
                
                // TODO cambiar rutas imagenes

                arrayListPasivas.stream().map((_item) -> {
                    nombreDelCampeon = nombreCampeonCombo.getSelectedItem().toString();
                    return _item;
                }).map((_item) -> {
                    fotoP.setIcon(new ImageIcon(rutaPasivas + nombreDelCampeon + "_Passive" + ".png"));
                    return _item;
                }).map((_item) -> {
                    fotoQ.setIcon(new ImageIcon(rutaQ + nombreDelCampeon + "Q" + ".png"));
                    return _item;
                }).map((_item) -> {
                    fotoW.setIcon(new ImageIcon(rutaW + nombreDelCampeon + "W" + ".png"));
                    return _item;
                }).map((_item) -> {
                    fotoE.setIcon(new ImageIcon(rutaE + nombreDelCampeon + "E" + ".png"));
                    return _item;
                }).map((_item) -> {
                    fotoR.setIcon(new ImageIcon(rutaR + nombreDelCampeon + "R" + ".png"));
                    return _item;
                }).forEach((_item) -> {
                    imagenCampeon.setIcon(new ImageIcon(rutaCampeones + nombreDelCampeon + ".png"));
                });

                break;
        }
    }

    public void inicializarFuentes() {
        Properties propiedades = new ArchivoPropiedades().getProperties();

        cuerpo = new FontUIResource(
                propiedades.getProperty("textoFuente"),
                Font.PLAIN,
                Integer.parseInt(propiedades.getProperty("textoTamano"))
        );

        titulo = new FontUIResource(
                propiedades.getProperty("tituloFuente"),
                Font.BOLD,
                Integer.parseInt(propiedades.getProperty("tituloTamano"))
        );

    }
}
