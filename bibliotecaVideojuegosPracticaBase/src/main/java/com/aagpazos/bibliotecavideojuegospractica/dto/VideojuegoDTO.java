package com.aagpazos.bibliotecavideojuegospractica.dto;

public class VideojuegoDTO {
    private Integer id;
    private String titulo;
    private Integer añoLanzamiento;
    private Integer pegi;
    private Integer idDesarrolladora;
    private String desarrolladora;
    private Integer copiasTotales;
    private Integer copiasDisponibles;
    private boolean disponible;
    private Integer totalPrestado;

    //Constructor vacio, necesario para que funcione OK
    public VideojuegoDTO() {}

    //Constructor "normal"
    public VideojuegoDTO(Integer id, String titulo, Integer añoLanzamiento, Integer pegi, Integer idDesarrolladora, String desarrolladora, Integer copiasTotales, Integer copiasDisponibles) {
        this.id = id;
        this.titulo = titulo;
        this.añoLanzamiento = añoLanzamiento;
        this.pegi = pegi;
        this.idDesarrolladora = idDesarrolladora;
        this.desarrolladora = desarrolladora;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasDisponibles;
    }

    //Constructor filtros
    public VideojuegoDTO(String titulo, Integer idDesarrolladora, Integer añoDesde,
                         Integer pegiMax, boolean soloDisponibles) {
        this.titulo = titulo;
        this.idDesarrolladora = idDesarrolladora;
        this.añoLanzamiento = añoDesde;
        this.pegi = pegiMax;
        this.disponible = soloDisponibles;
    }

    //Constructor request
    public VideojuegoDTO(String titulo, Integer añoLanzamiento, Integer pegi, Integer idDesarrolladora) {
        this.titulo = titulo;
        this.idDesarrolladora = idDesarrolladora;
        this.pegi = pegi;
        this.añoLanzamiento = añoLanzamiento;
    }

    //Constructor más prestados
    public VideojuegoDTO(Integer id, String titulo, Integer totalPrestado) {
        this.id = id;
        this.titulo = titulo;
        this.totalPrestado = totalPrestado;
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

    public Integer getAñoLanzamiento() {
        return añoLanzamiento;
    }

    public void setAñoLanzamiento(Integer añoLanzamiento) {
        this.añoLanzamiento = añoLanzamiento;
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

    public Integer getTotalPrestado() {
        return totalPrestado;
    }

    public void setTotalPrestado(Integer totalPrestado) {
        this.totalPrestado = totalPrestado;
    }
}
