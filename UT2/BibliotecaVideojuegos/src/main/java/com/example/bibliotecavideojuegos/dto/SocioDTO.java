package com.example.bibliotecavideojuegos.dto;

import java.time.LocalDate;

public class SocioDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaNacimiento;
    private Boolean activo;
    private Integer prestamosAbiertos;
    private LocalDate fechaAlta;

    public SocioDTO(Integer id, String nombre, String apellidos, String email, LocalDate fechaNacimiento,
                    Boolean activo, Integer prestamosAbiertos, LocalDate fechaAlta) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
        this.prestamosAbiertos = prestamosAbiertos;
        this.fechaAlta = fechaAlta;
    }

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

    public Integer getEdad() {
        LocalDate hoy = LocalDate.now();
        int edad = hoy.getYear() - fechaNacimiento.getYear();
        return edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(Integer prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
