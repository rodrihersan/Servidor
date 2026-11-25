package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen estadístico de la biblioteca")
public class ResumenDTO {

    @Schema(description = "Número total de videojuegos", example = "120")
    private int videojuegos;

    @Schema(description = "Número de socios activos", example = "85")
    private int sociosActivos;

    @Schema(description = "Número de préstamos abiertos", example = "20")
    private int prestamosAbiertos;

    @Schema(description = "Número de préstamos vencidos", example = "5")
    private int prestamosVencidos;

    public ResumenDTO() {}

    public ResumenDTO(int videojuegos, int sociosActivos, int prestamosAbiertos, int prestamosVencidos) {
        this.videojuegos = videojuegos;
        this.sociosActivos = sociosActivos;
        this.prestamosAbiertos = prestamosAbiertos;
        this.prestamosVencidos = prestamosVencidos;
    }

    public int getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(int videojuegos) {
        this.videojuegos = videojuegos;
    }

    public int getSociosActivos() {
        return sociosActivos;
    }

    public void setSociosActivos(int sociosActivos) {
        this.sociosActivos = sociosActivos;
    }

    public int getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(int prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }

    public int getPrestamosVencidos() {
        return prestamosVencidos;
    }

    public void setPrestamosVencidos(int prestamosVencidos) {
        this.prestamosVencidos = prestamosVencidos;
    }
}