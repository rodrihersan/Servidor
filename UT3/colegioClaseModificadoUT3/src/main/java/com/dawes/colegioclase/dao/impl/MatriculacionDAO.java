package com.dawes.colegioclase.dao.impl;

import com.dawes.colegioclase.dao.interfaz.IMatriculacionDAO;
import com.dawes.colegioclase.repository.MatriculacionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class MatriculacionDAO implements IMatriculacionDAO {
    private final MatriculacionRepository matriculacionRepository;
    public MatriculacionDAO(MatriculacionRepository
                                    matriculacionRepository) {
        this.matriculacionRepository = matriculacionRepository;
    }

    @Override
    public int contarAsignaturasMatriculadas(Integer idAlumno) {
        return matriculacionRepository.contarAsignaturasMatriculadas(idAlumno);
    }

    @Override
    public int insertarMatriculacion(Integer idAlumno, int idAsignatura, LocalDate fecha, double tasa) {
        return matriculacionRepository.insertarMatriculacion(idAlumno, idAsignatura, fecha, tasa);
    }
}
