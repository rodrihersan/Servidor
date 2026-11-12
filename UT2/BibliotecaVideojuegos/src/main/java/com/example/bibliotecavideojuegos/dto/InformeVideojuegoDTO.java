package com.example.bibliotecavideojuegos.dto;

public class InformeVideojuegoDTO {
    private Integer idVideojuego;
    private String titulo;
    private Integer numeroPrestamos;

    public InformeVideojuegoDTO(Integer idVideojuego, String titulo, Integer numeroPrestamos) {
        this.idVideojuego = idVideojuego;
        this.titulo = titulo;
        this.numeroPrestamos = numeroPrestamos;
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

    public Integer getNumeroPrestamos() {
        return numeroPrestamos;
    }

    public void setNumeroPrestamos(Integer numeroPrestamos) {
        this.numeroPrestamos = numeroPrestamos;
    }
}
