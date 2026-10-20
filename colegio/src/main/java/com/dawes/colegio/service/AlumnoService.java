package com.dawes.colegio.service;

import com.dawes.colegio.dao.interfaz.IAlumnoDAO;
import com.dawes.colegio.dao.interfaz.IMunicipioDAO;
import com.dawes.colegio.dto.AlumnoDTO;
import com.dawes.colegio.dto.DesplegableDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlumnoService {
    private final IAlumnoDAO alumnoDAO;
    private final IMunicipioDAO municipioDAO;

    public AlumnoService(IAlumnoDAO alumnoDAO, IMunicipioDAO municipioDAO) {
        this.alumnoDAO = alumnoDAO;
        this.municipioDAO = municipioDAO;
    }

    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String
            apellido, boolean familiaNumerosa, boolean activo) {
        return alumnoDAO.buscarAlumnos(nombre, apellido,
                familiaNumerosa, activo);
    }

    public int insertarAlumno(AlumnoDTO alumno) {
        return alumnoDAO.insertarAlumno(alumno);
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableMunicipios() {
        return municipioDAO.listarParaDesplegable();
    }

    public int actualizarAlumno(AlumnoDTO alumno) {
        return alumnoDAO.actualizarAlumno(alumno);
    }

    public int eliminarAlumno(String id) {
        return alumnoDAO.eliminarAlumno(id);
    }
}

