/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Clases.Habilidad;
import Clases.Pasiva;
import Clases.Skin;
import Clases.Stats;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author MaQuiNa
 */
public interface Conector {

    void actualizarBarra(int valor);

    void cerrarBaseDatos(Connection conexion);

    Connection conectarBaseDatos();
    
    // --------------------- Muestra De Datos ----------------------------------

    String mostrarCosteIpCampeon(String nombre);

    ArrayList<Habilidad> mostrarHabilidad(String nombreBuscar);

    String mostrarLore(String nombre);

    String mostrarNombreMote(String nombre);

    ArrayList<Pasiva> mostrarPasiva(String nombreBuscar);

    String mostrarRolCampeon(String nombre);

    ArrayList<Skin> mostrarSkins(String nombreBuscar);

    Stats mostrarStats(String nombreBuscar);

    ArrayList<String> nombresCampeones();
    // ----------------- Fin Muestra De Datos ----------------------------------
    
}
