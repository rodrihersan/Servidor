package com.dawes.ejerciciothymeleafbasico.modelo;

public class Estudiante {
    private String nombre;
    private String apellido;
    private String asignatura;

    public Estudiante(String nombre, String apellido, String asignatura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.asignatura = asignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

        public String getAsignatura() {
            return asignatura;
        }

        public void setAsignatura(String asignatura) {
            this.asignatura = asignatura;
        }
    }
