package com.example.bibliotecavideojuegos.dto;

import java.time.LocalDate;

public class PrestamoDTO {
    private Integer id;
    private Integer idSocio;
    private Integer idCopia;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private double multa;
    private String socio;
    private String videojuego;
    private String codigoInventario;
    private String estado;
    private Integer diasRetraso;
    private String plataforma;

    public PrestamoDTO() {
    }

    public PrestamoDTO(Integer id, Integer idSocio, Integer idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion, double multa) {
        this.id = id;
        this.idSocio = idSocio;
        this.idCopia = idCopia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
    }

    public PrestamoDTO(Integer id, Integer idSocio, Integer idCopia, String socio, String videojuego, String codigoInventario, LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion, double multa, String estado, Integer diasRetraso) {
        this.id = id;
        this.idSocio = idSocio;
        this.idCopia = idCopia;
        this.socio = socio;
        this.videojuego = videojuego;
        this.codigoInventario = codigoInventario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
        this.estado = estado;
        this.diasRetraso = diasRetraso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
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

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public String getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(String videojuego) {
        this.videojuego = videojuego;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(Integer diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}