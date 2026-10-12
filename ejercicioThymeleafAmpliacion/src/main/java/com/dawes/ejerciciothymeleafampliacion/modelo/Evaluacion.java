package com.dawes.ejerciciothymeleafampliacion.modelo;

public class Evaluacion {
    private Alumno alumno;
    private int nota;

    public Evaluacion(Alumno alumno, int nota) {
        this.alumno = alumno;
        this.nota = nota;
    }

    public String getResultado() {
        if (this.nota >= 5) {
            return "aprobado";
        } else {
            return "suspendido";
        }
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}

