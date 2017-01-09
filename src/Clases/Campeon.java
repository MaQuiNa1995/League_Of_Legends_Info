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
public class Campeon {

    int   id;
    
    String   mote,
             nombre,
             rol,
             lore;
    
    int costeIP;

    public Campeon(int id, String mote, String nombre, String rol, String lore,int costeIP) {
        this.id = id;
        this.mote = mote;
        this.nombre = nombre;
        this.rol = rol;
        this.lore = lore;
        this.costeIP=costeIP;
    }

    public int getCosteIP() {
        return costeIP;
    }

    public void setCosteIP(int costeIP) {
        this.costeIP = costeIP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMote() {
        return mote;
    }

    public void setMote(String mote) {
        this.mote = mote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }
    
    
}