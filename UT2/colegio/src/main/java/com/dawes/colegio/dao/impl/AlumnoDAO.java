package com.dawes.colegio.dao.impl;

import com.dawes.colegio.dao.interfaz.IAlumnoDAO;
import com.dawes.colegio.dto.AlumnoDTO;
import com.dawes.colegio.repository.AlumnoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AlumnoDAO implements IAlumnoDAO {
    private final AlumnoRepository alumnoRepository;
    public AlumnoDAO(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }
    @Override
    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, boolean familiaNumerosa, boolean activo) {
        return alumnoRepository.buscarAlumnos(nombre, apellido, familiaNumerosa, activo);
    }

    @Override
    public int insertarAlumno(AlumnoDTO alumno) {
        return alumnoRepository.insertar(alumno);
    }

    @Override
    public int actualizarAlumno(AlumnoDTO alumno) {
        return alumnoRepository.actualizar(alumno);
    }

    @Override
    public int eliminarAlumno(String id) {
        return alumnoRepository.eliminar(id);
    }
}

