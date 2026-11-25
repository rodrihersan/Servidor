package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Información de un socio de la biblioteca")
public class SocioDTO {

    @Schema(description = "Identificador del socio", example = "1")
    private Integer id;

    @Schema(description = "Nombre del socio", example = "Juan")
    private String nombre;

    @Schema(description = "Apellidos del socio", example = "García López")
    private String apellidos;

    @Schema(description = "Correo electrónico", example = "juan@gmail.com")
    private String email;

    @Schema(description = "Fecha de nacimiento", example = "1995-05-20")
    private LocalDate fechaNacimiento;

    @Schema(description = "Fecha de alta en la biblioteca", example = "2026-01-15")
    private LocalDate fechaAlta;

    @Schema(description = "Indica si el socio está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Edad calculada del socio", example = "30")
    private Integer edad;

    @Schema(description = "Número de préstamos abiertos", example = "2")
    private Integer prestamosAbiertos;

    public SocioDTO() {}

    public SocioDTO(Integer id, String nombre, String apellidos, String email, LocalDate fechaNacimiento, LocalDate fechaAlta, Boolean activo, Integer edad, Integer prestamosAbiertos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
        this.edad = edad;
        this.prestamosAbiertos = prestamosAbiertos;
    }

    //Socio Filtro
    public SocioDTO(String nombre, String email, Boolean activo) {
        this.nombre = nombre;
        this.email = email;
        this.activo = activo;
    }

    //Socio Request
    public SocioDTO(String nombre, String apellidos, String email,
                    LocalDate fechaNacimiento, Boolean activo){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
    }

    //Socio

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(Integer prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }
}