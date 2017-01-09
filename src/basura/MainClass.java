/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basura;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author MaQuiNa
 */
public class MainClass {

    public static void main(String[] args) {
        String letra = "C:\\Users\\MaQuiNa\\InfoLol\\Imagenes\\Skins";
        File Dir = new File(letra);
        File[] lista_Archivos = Dir.listFiles();
        for (int i = 0; i < lista_Archivos.length; i++) {

            if (lista_Archivos[i].isDirectory() && !lista_Archivos[i].isHidden()) {

                String Pregunta = lista_Archivos[i].getName();
                
                System.out.println("SetOutPath $INSTDIR\\Imagenes\\Skins\\"+Pregunta);
                System.out.println("File W.\\Imagenes\\Skins\\" + Pregunta + "\\W");
                  
            }
        }
    }
    
                

    static void Meter_String(String Pregunta) {
        try {

            FileWriter Escribir_Fichero = new FileWriter("C:\\Users\\MaQuiNa\\Desktop\\ARCHIVO.TXT", true);
BufferedWriter bw = new BufferedWriter(Escribir_Fichero);
            // Metemos en el fichero lo que escribio el usuario acompañado de un salto de linea
            Escribir_Fichero.write("File W.\\Imagenes\\Skins\\" + Pregunta + "\\W");
            bw.newLine();
            // Cerramos el flujo
            Escribir_Fichero.close();
            bw.close();

            // Capturamos excepciones
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

    }
    static void Meter_String2(String Pregunta) {
        try {

            FileWriter Escribir_Fichero = new FileWriter("C:\\Users\\MaQuiNa\\Desktop\\ARCHIVO.TXT", true);
BufferedWriter bw = new BufferedWriter(Escribir_Fichero);
            // Metemos en el fichero lo que escribio el usuario acompañado de un salto de linea
            Escribir_Fichero.write("SetOutPath $INSTDIR\\Imagenes\\Skins\\"+Pregunta);
            bw.newLine();
            // Cerramos el flujo
            Escribir_Fichero.close();
            bw.close();

            // Capturamos excepciones
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

    }
}
