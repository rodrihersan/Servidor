package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Información de un préstamo de la biblioteca")
public class PrestamoDTO {

    @Schema(description = "Identificador del préstamo", example = "1")
    private Integer id;

    @Schema(description = "Identificador del socio", example = "7")
    private Integer idSocio;

    @Schema(description = "Nombre completo del socio", example = "Juan García")
    private String socio;

    @Schema(description = "Identificador de la copia", example = "2")
    private Integer idCopia;

    @Schema(description = "Código de inventario de la copia", example = "CP-0008")
    private String codigoInventario;

    @Schema(description = "Título del videojuego", example = "Minecraft")
    private String titulo;

    @Schema(description = "Plataforma del videojuego", example = "PC")
    private String plataforma;

    @Schema(description = "Fecha de realización del préstamo", example = "2026-11-24")
    private LocalDate fechaPrestamo;

    @Schema(description = "Fecha límite de devolución", example = "2026-12-09")
    private LocalDate fechaLimite;

    @Schema(description = "Fecha de devolución efectiva", example = "2026-12-08")
    private LocalDate fechaDevolucion;

    @Schema(description = "Multa acumulada en euros", example = "1.50")
    private Double multa;

    @Schema(description = "Estado del préstamo", example = "EN_PLAZO")
    private String estado;

    @Schema(description = "Días de retraso en la devolución", example = "3")
    private Integer diasRetraso;

    @Schema(description = "Número de renovaciones realizadas", example = "0")
    private Integer renovaciones;

    public PrestamoDTO() {}

    //PrestamoDTO
    public PrestamoDTO(Integer id, Integer idSocio, String socio, Integer idCopia, String codigoInventario, String titulo, String plataforma, LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion, Double multa, String estado, Integer diasRetraso, Integer renovaciones) {
        this.id = id;
        this.idSocio = idSocio;
        this.socio = socio;
        this.idCopia = idCopia;
        this.codigoInventario = codigoInventario;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
        this.estado = estado;
        this.diasRetraso = diasRetraso;
        this.renovaciones = renovaciones;
    }

    //Prestamo Filtro
    public PrestamoDTO(String socio, String titulo, String estado, LocalDate desde) {
        this.socio = socio;
        this.titulo = titulo;
        this.estado = estado;
        this.fechaPrestamo = desde;
    }

    //Prestamo request
    public PrestamoDTO(Integer idSocio, Integer idCopia) {
        this.idSocio = idSocio;
        this.idCopia = idCopia;
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

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
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

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
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

    public Integer getRenovaciones() {
        return renovaciones;
    }

    public void setRenovaciones(Integer renovaciones) {
        this.renovaciones = renovaciones;
    }
}