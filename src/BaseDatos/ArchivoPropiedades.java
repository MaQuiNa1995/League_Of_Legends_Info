/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import GestionErrores.CrashReporterImpl;
import Clases.Rutas;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


public class ArchivoPropiedades extends Properties{
    
    static String claseActual="BaseDatos.ArchivoPropiedades";
    
    static final Logger ARCHIVOLOG = Logger.getLogger(claseActual);
    
    Properties propiedades;

    public Properties getProperties() {
        
        try {
            
            String directorioInstalacion= Rutas.rutaInstalacion;
            
            propiedades = new Properties();
            FileInputStream entrada = new FileInputStream(directorioInstalacion +"/InfoLol/ArchivosPropiedades/conf.properties");
            propiedades.load(entrada);
            if (!propiedades.isEmpty()) {
                return propiedades;
            } else {
                return null;
            }
        } catch (IOException ex) {
            //ARCHIVOLOG.severe(CrashReporter.fechaActual().concat(" Error al leer el archivo properties "+ex.getMessage()));
            CrashReporterImpl.meterCadena(CrashReporterImpl.fechaActual().concat(" Error al leer el archivo properties "+ex.getMessage()), claseActual);
            return null;
        }
    }
}
