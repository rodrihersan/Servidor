package com.dawes.colegioclase.dao.impl;

import com.dawes.colegioclase.dao.interfaz.IAlumnoDAO;
import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.dto.DesplegableDTO;
import com.dawes.colegioclase.repository.AlumnoRepository;
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
    public int eliminarAlumno(Integer id) {
        return alumnoRepository.eliminar(id);
    }

    @Override
    public ArrayList<AlumnoDTO> filtrarAlumnos(String nombre, String
                                                       apellido, Integer idMunicipio,
                                               Boolean familiaNumerosa,
                                               Boolean activo) {
        return alumnoRepository.filtrarAlumnos(nombre, apellido, idMunicipio,
                familiaNumerosa, activo);
    }

    @Override
    public AlumnoDTO obtenerAlumnoPorId(Integer id) {
        return alumnoRepository.obtenerAlumnoPorId(id);
    }

    @Override
    public boolean esFamiliaNumerosa(Integer idAlumno) {
        return alumnoRepository.esFamiliaNumerosa(idAlumno);
    }

    @Override
    public ArrayList<DesplegableDTO> listarAlumnosParaDesplegable() {
        return alumnoRepository.listarAlumnosParaDesplegable();
    }

    @Override
    public AlumnoDTO buscarPorId(String id) {
        return alumnoRepository.buscarPorId(id);
    }
}

