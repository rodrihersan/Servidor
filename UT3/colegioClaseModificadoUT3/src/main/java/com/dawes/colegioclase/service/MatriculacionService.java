package com.dawes.colegioclase.service;

import com.dawes.colegioclase.dao.interfaz.IAlumnoDAO;
import com.dawes.colegioclase.dao.interfaz.IAsignaturaDAO;
import com.dawes.colegioclase.dao.interfaz.IMatriculacionDAO;
import com.dawes.colegioclase.dto.DesplegableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MatriculacionService {
    private static final Logger logger = LoggerFactory.getLogger(MatriculacionService.class);
    private final IMatriculacionDAO matriculacionDAO;
    private final IAlumnoDAO alumnoDAO;
    private final IAsignaturaDAO asignaturaDAO;

    public MatriculacionService(IMatriculacionDAO matriculacionDAO, IAlumnoDAO alumnoDAO, IAsignaturaDAO asignaturaDAO) {
        this.matriculacionDAO = matriculacionDAO;
        this.alumnoDAO = alumnoDAO;
        this.asignaturaDAO = asignaturaDAO;
    }

    public Optional<Double> insertarMatriculacion(Integer idAlumno, int idAsignatura, LocalDate fecha) {
        double tasa = calcularTasa(idAlumno, idAsignatura);
        LocalDate fechaMatricula;
        if (fecha != null) {
            fechaMatricula = fecha;
        } else {
            fechaMatricula = LocalDate.now();
        }
        int filasAfectadas =
                matriculacionDAO.insertarMatriculacion(idAlumno, idAsignatura, fechaMatricula, tasa);
        if (filasAfectadas > 0) {
            return Optional.of(tasa);
        } else {
            return Optional.empty();
        }
    }

    private double calcularTasa(Integer idAlumno, int idAsignatura) {
        double tasaBase = asignaturaDAO.obtenerTasaAsignatura(idAsignatura);
        int numAsignaturasMatriculadas = matriculacionDAO.contarAsignaturasMatriculadas(idAlumno);
        boolean familiaNumerosa = alumnoDAO.esFamiliaNumerosa(idAlumno);
        double tasaFinal = tasaBase;
        if (numAsignaturasMatriculadas >= 3 &&
                numAsignaturasMatriculadas <= 5) {
            tasaFinal = tasaFinal * 0.70; // 30% de descuento
        } else if (numAsignaturasMatriculadas >= 6) {
            tasaFinal = tasaFinal * 0.50; // 50% de descuento
        }
        if (familiaNumerosa) {
            tasaFinal = tasaFinal * 0.50; // 50% adicional
        }
        logger.debug("Tasa calculada para el alumno {} en la asignatura {}: {}", idAlumno, idAsignatura, tasaFinal);
        return tasaFinal;
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableAlumnos() {
        return alumnoDAO.listarAlumnosParaDesplegable();
    }
    public ArrayList<DesplegableDTO> obtenerDesplegableAsignaturas() {
        return asignaturaDAO.listarAsignaturasParaDesplegable();
    }

}
