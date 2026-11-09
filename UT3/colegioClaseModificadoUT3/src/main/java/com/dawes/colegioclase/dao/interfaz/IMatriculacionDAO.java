package com.dawes.colegioclase.dao.interfaz;


import java.time.LocalDate;

public interface IMatriculacionDAO {
    int contarAsignaturasMatriculadas(Integer idAlumno);
    int insertarMatriculacion(Integer idAlumno, int idAsignatura, LocalDate fecha, double tasa);
}
