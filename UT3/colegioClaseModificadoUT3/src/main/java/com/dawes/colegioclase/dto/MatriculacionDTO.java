package com.dawes.colegioclase.dto;

import java.time.LocalDate;

public class MatriculacionDTO {
    private Integer idAlumno;
    private Integer idAsignatura;
    private LocalDate fecha;

    public MatriculacionDTO() {
    }

    public MatriculacionDTO(Integer idAlumno, Integer idAsignatura, LocalDate fecha) {
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.fecha = fecha;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Integer idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
