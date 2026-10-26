package com.dawes.hernandezsanchezrodrigo_biblioteca.dto;

import java.time.LocalDate;

public class PrestamoDTO {
    private Integer id;
    private Integer id_socio;
    private Integer id_copia;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;

    public PrestamoDTO(int id, int id_socio, int id_copia, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        this.id = id;
        this.id_socio = id_socio;
        this.id_copia = id_copia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public int getId_copia() {
        return id_copia;
    }

    public void setId_copia(int id_copia) {
        this.id_copia = id_copia;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
