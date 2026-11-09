package com.dawes.ejerciciothymeleafbasico.modelo;

public class Personaje {
    private String nombre;
    private String clase;
    private int nivel;
    private String arma;

    public Personaje(String nombre, int nivel, String clase, String arma){
        this.nombre = nombre;
        this.nivel = nivel;
        this.clase = clase;
        this.arma = arma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }
}
