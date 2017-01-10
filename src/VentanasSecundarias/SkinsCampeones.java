/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentanasSecundarias;

import BaseDatos.ConectorImpl;
import Clases.Rutas;
import Clases.Skin;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.plaf.FontUIResource;

public final class SkinsCampeones extends JFrame implements ActionListener, ItemListener {

    private final Font arial = new FontUIResource("Arial", Font.BOLD, 15);

    ConectorImpl con;

    String rutaSplashArt = Rutas.rutaInstalacion+"/InfoLol/Imagenes/Skins/",
            nombreBuscar;

    JPanel panelTodo;

    JLabel skinLabel, imagenLabel, costeLabel, costeLabel2;
    JComboBox<String> skinSelector;
    JComboBox<String> nombreCampeonCombo;
    ArrayList<String> arrayCampeones;
    ArrayList<Skin> arrayListSkins;

    // --- Panel ---
    public SkinsCampeones() {
        con = new ConectorImpl();
        
        definirVentana();
        definirPanelTodo();
        definirPanelArriba();
        definirPanelImagen();

        this.add(panelTodo);
        this.setVisible(true);
    }

    void definirVentana() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Aspectos De Los Campeones");
    }

    void definirPanelTodo() {
        panelTodo = new JPanel();
        panelTodo.setLayout(new BorderLayout());
    }
    
    void definirPanelArriba() {

        JLabel nombreLabel;
        JButton buscar;
        
        JPanel panelArriba = new JPanel(), panelNombre, panelSkin,panelCoste;
        
        panelArriba.setLayout(new BoxLayout(panelArriba, BoxLayout.Y_AXIS));

        panelNombre = new JPanel(new FlowLayout());
        nombreLabel = new JLabel("Nombre Campeón:");
        nombreLabel.setFont(arial);
        nombreCampeonCombo = new JComboBox<>();

        arrayCampeones = new ArrayList<>();
        arrayCampeones = con.nombresCampeones();
        
        arrayCampeones.stream().forEach((objetoSacado) -> {
            nombreCampeonCombo.addItem(objetoSacado);
        });
        
        buscar = new JButton("Buscar");
        buscar.addActionListener(this);

        panelNombre.add(nombreLabel);
        panelNombre.add(nombreCampeonCombo);
        panelNombre.add(buscar);

        panelSkin = new JPanel(new FlowLayout());
        skinLabel = new JLabel("Selecciona Skin:");
        skinLabel.setFont(arial);
        skinSelector = new JComboBox<>();
        skinSelector.addItemListener(this);

        skinSelector.setVisible(false);
        skinLabel.setVisible(false);

        panelCoste = new JPanel();
        costeLabel = new JLabel("Coste Rp:");
        costeLabel.setFont(arial);
        costeLabel2 = new JLabel("");
        costeLabel2.setFont(arial);

        costeLabel.setVisible(false);
        costeLabel2.setVisible(false);

        panelSkin.add(skinLabel);
        panelSkin.add(skinSelector);

        panelSkin.add(costeLabel);
        panelSkin.add(costeLabel2);

        panelArriba.add(panelNombre);
        panelArriba.add(panelSkin);
        panelArriba.add(panelCoste);

        panelTodo.add(panelArriba, BorderLayout.NORTH);
    }

    void definirPanelImagen() {
        JPanel panelImagen = new JPanel();
                imagenLabel = new JLabel();

        panelImagen.add(imagenLabel, BorderLayout.CENTER);

        panelTodo.add(panelImagen);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        nombreBuscar = nombreCampeonCombo.getSelectedItem().toString();
        switch (ae.getActionCommand()) {

            case "Buscar":
                
                skinSelector.removeAllItems();
                
                skinSelector.setVisible(true);
                skinLabel.setVisible(true);
                this.arrayListSkins = con.mostrarSkins(nombreBuscar);
                arrayListSkins.stream().forEach((objetoSacado) -> {
                    skinSelector.addItem(objetoSacado.getNombre());
                });

                break;
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == skinSelector) {
            String seleccionado = (String) skinSelector.getSelectedItem();

            arrayListSkins.stream().filter((objetoSacado) -> (objetoSacado.getNombre().equals(seleccionado))).map((objetoSacado) -> {
                costeLabel.setVisible(true);
                costeLabel2.setVisible(true);
                imagenLabel.setIcon(new ImageIcon(rutaSplashArt + nombreBuscar + "/" + objetoSacado.getImagen()));
                return objetoSacado;
            }).forEach((objetoSacado) -> {
                costeLabel2.setText("" + objetoSacado.getCosteRP());
            });

        }
    }
}
