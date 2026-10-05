package com.dawes.hernandezsanchezrodrigopracticaexamen1.modelo;

import java.util.Random;

public class Juego {
    private String titulo;
    private String genero;
    private double precio;
    private int horasJugadas;

    public Juego(String titulo, String genero, double precio, int horasJugadas) {
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.horasJugadas = horasJugadas;
    }

    public double getDescuentoAleatorio(){
        Random r = new Random();
        int descuentoAleatorio = r.nextInt(75 - 10 + 1) + 10;
        return descuentoAleatorio;
    }

    public double getDescuento(){
        double descuento = (this.precio * (getDescuentoAleatorio() / 100));
        return descuento;
    }

    public double getFinalTrasDescuento(){
        return this.precio - getDescuento();
    }

    public double costeHora(){
        return this.horasJugadas / this.precio;
    }

    public String tipoJugador(){
        String tipoJugador;
        if(this.horasJugadas >=0 && this.horasJugadas < 10){
            return tipoJugador = "casual";
        }else if (this.horasJugadas >= 10 && this.horasJugadas <=99){
            return tipoJugador = "Habitual";
        }else if (this.horasJugadas >=100){
            return tipoJugador = "Adicto";
        }else {
            return tipoJugador = "No has jugado";
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(int horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
