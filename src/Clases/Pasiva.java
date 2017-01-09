/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 * @Autor cmunoz
 * @Fecha 13-oct-2016
 */
public class Pasiva {

    String nombre,
            descripcion,
            enfriamiento;
            
    int a_campeon;

    public Pasiva(String nombre, String descripcion, String enfriamiento, int a_campeon) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.enfriamiento = enfriamiento;
        this.a_campeon = a_campeon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnfriamiento() {
        return enfriamiento;
    }

    public void setEnfriamiento(String enfriamiento) {
        this.enfriamiento = enfriamiento;
    }

    public int getA_campeon() {
        return a_campeon;
    }

    public void setA_campeon(int a_campeon) {
        this.a_campeon = a_campeon;
    }
}
