package com.dawes.ejerciciothymeleafampliacion.modelo;

import java.time.LocalDate;
import java.time.Period;

public class Alumno {
    private String nombre;
    private LocalDate fechaNacimiento;
    private String ciudad;

    public Alumno(String nombre, LocalDate fechaNacimiento, String ciudad) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
    }

    public int getEdad() {
        return LocalDate.now().getYear() - this.fechaNacimiento.getYear();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}