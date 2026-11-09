package com.dawes.practicaexamen.modelo;

import java.util.Random;

public class Equipo {
    private String nombre;
    private String region;
    private int fuerza;

    public Equipo(String nombre, String region, int fuerza) {
        this.nombre = nombre;
        this.region = region;
        this.fuerza = fuerza;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Random r = new Random();
        //this.fuerza = r.nextInt(40) + 60; // 60 a 99
    }


