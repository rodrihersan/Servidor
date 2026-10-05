package com.dawes.hernandezsanchezrodrigopracticaexamen1.modelo;

import java.time.LocalDate;

public class Usuario {
    private String nick;
    private String pais;
    private LocalDate fechaRegistro;

    public Usuario(String nick, String pais, LocalDate fechaRegistro) {
        this.nick = nick;
        this.pais = pais;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
