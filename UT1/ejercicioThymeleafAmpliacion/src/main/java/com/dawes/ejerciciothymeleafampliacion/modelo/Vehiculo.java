package com.dawes.ejerciciothymeleafampliacion.modelo;

import java.time.LocalDate;

public class Vehiculo {
    private String marca;
    private String modelo;
    private int anio;

    public Vehiculo(String marca, String modelo, int anio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
    }

    public int getAntiguedad() {
        return LocalDate.now().getYear() - this.anio;
    }

    public String getEtiqueta() {
        if (this.anio < 2000) {
            return "Sin etiqueta";
        } else if (this.anio >=2000 && this.anio <= 2014) {
            return "B";
        } else {
            return "C";
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
