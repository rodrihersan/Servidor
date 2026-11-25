package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de una copia física de un videojuego")
public class CopiaDTO {

    @Schema(description = "Identificador de la copia", example = "1")
    private Integer id;

    @Schema(description = "Código de inventario", example = "CP-0008")
    private String codigoInventario;

    @Schema(description = "Identificador del videojuego", example = "1")
    private Integer idVideojuego;

    @Schema(description = "Título del videojuego", example = "Minecraft")
    private String titulo;

    @Schema(description = "Identificador de la plataforma", example = "1")
    private Integer idPlataforma;

    @Schema(description = "Nombre de la plataforma", example = "PC")
    private String plataforma;

    @Schema(description = "Estado físico de la copia", example = "BUENO")
    private String estado;

    @Schema(description = "Indica si la copia está activa", example = "true")
    private Boolean activa;

    @Schema(description = "Indica si la copia está disponible", example = "true")
    private Boolean disponible;

    public CopiaDTO() {}

    public CopiaDTO(Integer id, String codigoInventario, Integer idVideojuego, String titulo, Integer idPlataforma, String plataforma, String estado, Boolean activa, Boolean disponible) {
        this.id = id;
        this.codigoInventario = codigoInventario;
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.idPlataforma = idPlataforma;
        this.plataforma = plataforma;
        this.estado = estado;
        this.activa = activa;
        this.disponible = disponible;
    }

    //FILTRO
    public CopiaDTO(String titulo, Integer idPlataforma, String estado, boolean soloDisponibles){
        this.titulo = titulo;
        this.idPlataforma = idPlataforma;
        this.estado = estado;
        this.disponible = soloDisponibles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id;}

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(Integer idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}