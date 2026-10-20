package com.dawes.colegio.service;

import com.dawes.colegio.dao.interfaz.IAlumnoDAO;
import com.dawes.colegio.dto.AlumnoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlumnoService {
    private final IAlumnoDAO alumnoDAO;
    public AlumnoService(IAlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, boolean familiaNumerosa, boolean activo) {
        return alumnoDAO.buscarAlumnos(nombre, apellido, familiaNumerosa, activo);
    }
}

