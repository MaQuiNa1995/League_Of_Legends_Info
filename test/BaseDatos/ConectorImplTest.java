/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Clases.Habilidad;
import Clases.Pasiva;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConectorImplTest {

    private ConectorImpl con;

    @Before
    public void setUp() {
        con = new ConectorImpl();
    }

    // Test Método conectarBaseDatos()
    @Test
    public void testConexionBaseDatosNoNula() throws Exception {
        Connection result = con.conectarBaseDatos();
        assertNotNull(result);
    }

    @Test
    public void testConexionBaseDatos() throws Exception {
        Connection result = con.conectarBaseDatos();
        assertFalse(result.isClosed());
    }

    // Test Método mostrarNombreMote() uno con tildes (Kalista) o otro sin ellas (Kayle)
    @Test
    public void testNombreMoteDeKayle() {
        String nombre = "Kayle";
        String expResult = "Kayle , La Justiciera";
        String result = con.mostrarNombreMote(nombre);
        assertEquals(expResult, result);
    }

    @Test
    public void testNombreMoteDeKalista() {
        String nombre = "Kalista";
        String expResult = "Kalista , El Espíritu de la Venganza";
        String result = con.mostrarNombreMote(nombre);
        assertEquals(expResult, result);
    }

    // Test Método mostrarLore()
    @Test
    public void testMostrarLoreJanna() {
        String nombre = "Janna";
        String expResult = "Hay hechiceros que se entregan";
        String result = con.mostrarLore(nombre);
        assertTrue(result.startsWith(expResult));
    }

    // Test Método cerrarBaseDatos()
    @Test
    public void testCerrarBaseDatos() throws Exception {
        Connection conexion = con.conectarBaseDatos();
        con.cerrarBaseDatos(conexion);
        assertTrue(conexion.isClosed());
    }

    // Test Métodos Crear Tablas En La Base De Datos
    @Test
    public void testCrearTablaCampeones() {
        boolean errorAlCrear = con.crearTablaCampeones();
        assertTrue(errorAlCrear);
    }

    @Test
    public void testCrearTablaPasiva() {
        boolean errorAlCrear = con.crearTablaPasiva();
        assertTrue(errorAlCrear);
    }

    @Test
    public void testCrearTablaHabilidad() {
        boolean errorAlCrear = con.crearTablaHabilidad();
        assertTrue(errorAlCrear);
    }

    @Test
    public void testCrearTablaSkins() {
        boolean errorAlCrear = con.crearTablaSkins();
        assertTrue(errorAlCrear);
    }

    @Test
    public void testCrearTablaStats() {
        boolean errorAlCrear = con.crearTablaStats();
        assertTrue(errorAlCrear);
    }

    // Métodos Inserción De Datos
//    @Test
//    public void testGuardarObjetoCampeon() throws Exception {
//        boolean errorAlMeter = con.guardarObjetoCampeon();
//        assertTrue(errorAlMeter);
//    }
//
//    @Test
//    public void testGuardarObjetoPasiva() throws Exception {
//        boolean errorAlMeter = con.guardarObjetoPasiva();
//        assertFalse(errorAlMeter);
//    }
//
//    @Test
//    public void testGuardarObjetoStat() throws Exception {
//        boolean errorAlMeter = con.guardarObjetoStat();
//        assertFalse(errorAlMeter);
//    }
//
//    //
//    @Test
//    public void testGuardarObjetoHabilidad() throws Exception {
//        boolean errorAlMeter = con.guardarObjetoHabilidad();
//        assertFalse(errorAlMeter);
//    }
//
//    //
//    @Test
//    public void testGuardarObjetoSkin() throws Exception {
//        boolean errorAlMeter = con.guardarObjetoSkin();
//        assertFalse(errorAlMeter);
//    }
    
    // Test Del Método mostrarCosteIpCampeon()
    @Test
    public void testMostrarIpCampeonCamille() {
        String nombre = "Camille";
        String expResult = "6300";
        String result = con.mostrarCosteIpCampeon(nombre);
        assertEquals(expResult, result);
    }

    @Test
    public void testMostrarIpCampeonNulo() {
        String nombre = "nulo";
        String expResult = null;
        String result = con.mostrarCosteIpCampeon(nombre);
        assertEquals(expResult, result);
    }

    // Test Método mostrarHabilidad()
    @Test
    public void testMostrarHabilidadMordekaiser() {
        String nombreBuscar = "Mordekaiser";
        ArrayList<Habilidad> expResult = new ArrayList<>();
        ArrayList<Habilidad> result = con.mostrarHabilidad(nombreBuscar);
        assertTrue(expResult.equals(result));
    }

    @Test
    public void testMostrarHabilidadNoNuloMordekaiser() {
        String nombreBuscar = "Mordekaiser";
        ArrayList<Habilidad> result = con.mostrarHabilidad(nombreBuscar);
        assertNotNull(result);
    }

    // Test Método mostrarRolCampeon()
    @Test
    public void testMostrarRolMorgana() {
        String nombre = "Morgana";
        String expResult = "Apoyo";
        String result = con.mostrarRolCampeon(nombre);
        assertEquals(expResult, result);
    }

    @Test
    public void testMostrarRolNulo() {
        String nombre = "nulo";
        String result = con.mostrarRolCampeon(nombre);
        assertNull(result);
    }

    // Test Método mostrarPasiva()
    @Test
    public void testMostrarPasivaAkali() {
        String nombreBuscar = "Akali";
        List<Pasiva> expResult = new ArrayList<>();
        List<Pasiva> result = con.mostrarPasiva(nombreBuscar);
        assertTrue(expResult.equals(result));
        
    }
    
       @Test
    public void testMostrarPasivaNoNuloAkali() {
           String nombreBuscar = "Akali";
           ArrayList<Pasiva> result = con.mostrarPasiva(nombreBuscar);
           assertNotNull(result);
    }
    
    //
    //    @Test
    //    public void testMostrarSkins() {
    //        System.out.println("mostrarSkins");
    //        String nombreBuscar = "";
    //        Conector instance = new Conector();
    //        ArrayList<Skin> expResult = null;
    //        ArrayList<Skin> result = instance.mostrarSkins(nombreBuscar);
    //        assertEquals(expResult, result);
    //        fail("The test case is a prototype.");
    //    }
    //
    //    @Test
    //    public void testMostrarStats() {
    //        System.out.println("mostrarStats");
    //        String nombreBuscar = "";
    //        Conector instance = new Conector();
    //        Stats expResult = null;
    //        Stats result = instance.mostrarStats(nombreBuscar);
    //        assertEquals(expResult, result);
    //        fail("The test case is a prototype.");
    //    }
    //
    //    @Test
    //    public void testNombresCampeones() {
    //        System.out.println("nombresCampeones");
    //        Conector instance = new Conector();
    //        ArrayList<String> expResult = null;
    //        ArrayList<String> result = instance.nombresCampeones();
    //        assertEquals(expResult, result);
    //        fail("The test case is a prototype.");
    //    }
    //
  
}
