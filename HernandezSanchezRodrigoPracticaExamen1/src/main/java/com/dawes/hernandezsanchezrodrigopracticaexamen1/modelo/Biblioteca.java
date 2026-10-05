package com.dawes.hernandezsanchezrodrigopracticaexamen1.modelo;

public class Biblioteca {
    private Juego juego1;
    private Juego juego2;
    private Juego juego3;

    public Biblioteca(Juego juego1, Juego juego2, Juego juego3) {
        this.juego1 = juego1;
        this.juego2 = juego2;
        this.juego3 = juego3;
    }

    public int getHorasTotales(){
        int suma = this.juego1.getHorasJugadas() +
                this.juego2.getHorasJugadas() +
                this.juego3.getHorasJugadas();
        return suma;
    }

    public double getValorTotal(){
        double valor = this.juego1.getPrecio() +
                this.juego2.getPrecio() +
                this.juego3.getPrecio();
        return valor;
    }

    public String getJuegoMasJugado(){
        String juegoMasJugado;
        if(this.juego1.getHorasJugadas() > this.juego2.getHorasJugadas() && this.juego1.getHorasJugadas() > this.juego3.getHorasJugadas()){
            return juegoMasJugado = this.juego1.getGenero();
        }else if(this.juego2.getHorasJugadas() > this.juego1.getHorasJugadas() && this.juego2.getHorasJugadas() > this.juego3.getHorasJugadas()){
            return juegoMasJugado = this.juego2.getGenero();
        }else if(this.juego3.getHorasJugadas() > this.juego1.getHorasJugadas() && this.juego3.getHorasJugadas() > this.juego2.getHorasJugadas()){
            return juegoMasJugado = this.juego3.getGenero();
        }else{
            return juegoMasJugado = "Error";
        }
    }
    public Juego getJuego1() {
        return juego1;
    }

    public void setJuego1(Juego juego1) {
        this.juego1 = juego1;
    }

    public Juego getJuego2() {
        return juego2;
    }

    public void setJuego2(Juego juego2) {
        this.juego2 = juego2;
    }

    public Juego getJuego3() {
        return juego3;
    }

    public void setJuego3(Juego juego3) {
        this.juego3 = juego3;
    }
}
