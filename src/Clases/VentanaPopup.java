package Clases;

import javax.swing.JOptionPane;

public class VentanaPopup {

    public static void mostrarVentana(String Titulo, String Contenido, int opcion) {
        switch (opcion) {
            case 1:
                JOptionPane.showMessageDialog(null, Contenido, Titulo, JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, Contenido, Titulo, JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, Contenido, Titulo, JOptionPane.WARNING_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(null, Contenido, Titulo, JOptionPane.PLAIN_MESSAGE);
                break;
        }

    }

}
