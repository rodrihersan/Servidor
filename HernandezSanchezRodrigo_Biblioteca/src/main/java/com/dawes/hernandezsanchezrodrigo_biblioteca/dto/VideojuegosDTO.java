package com.dawes.hernandezsanchezrodrigo_biblioteca.dto;

import java.time.LocalDate;

public class VideojuegosDTO {
    private Integer id;
    private String titulo;
    private Integer anioLanzamiento;
    private Integer pegi;
    private Integer idDesarrolladora;
    private String desarrolladora;
    private Integer copiasTotales;
    private Integer copiasDisponibles;
    private boolean disponible;

    public VideojuegosDTO(Integer id, String titulo, Integer anioLanzamiento, Integer pegi,
                          Integer idDesarrolladora, String desarrolladora, Integer copiasTotales, Integer copiasDisponibles, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.anioLanzamiento = anioLanzamiento;
        this.pegi = pegi;
        this.idDesarrolladora = idDesarrolladora;
        this.desarrolladora = desarrolladora;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasDisponibles;
        this.disponible = disponible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(Integer anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public Integer getPegi() {
        return pegi;
    }

    public void setPegi(Integer pegi) {
        this.pegi = pegi;
    }

    public Integer getIdDesarrolladora() {
        return idDesarrolladora;
    }

    public void setIdDesarrolladora(Integer idDesarrolladora) {
        this.idDesarrolladora = idDesarrolladora;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public Integer getCopiasTotales() {
        return copiasTotales;
    }

    public void setCopiasTotales(Integer copiasTotales) {
        this.copiasTotales = copiasTotales;
    }

    public Integer getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(Integer copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
