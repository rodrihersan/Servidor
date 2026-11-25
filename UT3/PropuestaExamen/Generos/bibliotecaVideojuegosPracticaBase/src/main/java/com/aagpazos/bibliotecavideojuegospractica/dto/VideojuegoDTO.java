package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un videojuego de la biblioteca")
public class VideojuegoDTO {

    @Schema(description = "Identificador del videojuego", example = "1")
    private Integer id;

    @Schema(description = "Título del videojuego", example = "The Legend of Zelda")
    private String titulo;

    @Schema(description = "Año de lanzamiento", example = "2024")
    private Integer añoLanzamiento;

    @Schema(description = "Clasificación PEGI", example = "12")
    private Integer pegi;

    @Schema(description = "Identificador de la desarrolladora", example = "1")
    private Integer idDesarrolladora;

    //Ejemplo practico con Genero (tabla muchos a muchos. Un videojuego puede tener varios generos y
    //un mismo genero perteneceer a varios videojuegos
    @Schema(description = "Identificador del género", example = "1")
    private Integer idGenero;

    @Schema(description = "Nombre de la desarrolladora", example = "Nintendo")
    private String desarrolladora;

    @Schema(description = "Número total de copias", example = "10")
    private Integer copiasTotales;

    @Schema(description = "Número de copias disponibles", example = "7")
    private Integer copiasDisponibles;

    @Schema(description = "Indica si hay alguna copia disponible", example = "true")
    private boolean disponible;

    @Schema(description = "Número de veces que se ha prestado", example = "25")
    private Integer totalPrestado;

    public VideojuegoDTO() {}

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

    public VideojuegoDTO(String titulo, Integer idDesarrolladora, Integer añoDesde,
                         Integer pegiMax, boolean soloDisponibles) {
        this.titulo = titulo;
        this.idDesarrolladora = idDesarrolladora;
        this.añoLanzamiento = añoDesde;
        this.pegi = pegiMax;
        this.disponible = soloDisponibles;
    }

    public VideojuegoDTO(String titulo, Integer añoLanzamiento, Integer pegi, Integer idDesarrolladora) {
        this.titulo = titulo;
        this.idDesarrolladora = idDesarrolladora;
        this.pegi = pegi;
        this.añoLanzamiento = añoLanzamiento;
    }

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

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }
}