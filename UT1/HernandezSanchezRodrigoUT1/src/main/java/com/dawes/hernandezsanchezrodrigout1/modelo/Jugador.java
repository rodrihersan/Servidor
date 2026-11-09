package com.dawes.hernandezsanchezrodrigout1.modelo;

public class Jugador {
    private String nombre;
    private String equipo;
    private int puntos;
    private int rebotes;
    private int asitencias;
    private int perdidas;

    public Jugador(String nombre, String equipo, int puntos, int rebotes, int asitencias, int perdidas) {
        this.nombre = nombre;
        this.equipo = equipo;
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asitencias = asitencias;
        this.perdidas = perdidas;
    }

    public double getEficiencia(){
        if(this.perdidas == 0){
            return this.puntos + this.rebotes + this.asitencias;
        }else{
            return (double) (this.puntos + this.rebotes + this.asitencias) / this.perdidas;
        }
    }

    public String getValoracion(){
        String valoracion = "";
        if(this.getEficiencia() < 8){
            valoracion = "Rotacion";
            return valoracion;
        } else if ((this.getEficiencia() >= 8) && (this.getEficiencia() < 15)){
            valoracion = "Titular";
            return valoracion;
        }else{
            valoracion = "Estrella";
            return valoracion;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsitencias() {
        return asitencias;
    }

    public void setAsitencias(int asitencias) {
        this.asitencias = asitencias;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }
}
