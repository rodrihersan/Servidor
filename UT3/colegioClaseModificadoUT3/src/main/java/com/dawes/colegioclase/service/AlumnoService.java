package com.dawes.colegioclase.service;

import com.dawes.colegioclase.dao.impl.AlumnoDAO;
import com.dawes.colegioclase.dao.impl.MunicipioDAO;
import com.dawes.colegioclase.dao.interfaz.IAlumnoDAO;
import com.dawes.colegioclase.dao.interfaz.IMunicipioDAO;
import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.dto.DesplegableDTO;
import com.dawes.colegioclase.excepcion.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class AlumnoService {
    private final IAlumnoDAO alumnoDAO;
    private final IMunicipioDAO municipioDAO;

    public AlumnoService(IAlumnoDAO alumnoDAO, IMunicipioDAO municipioDAO) {
        this.alumnoDAO = alumnoDAO;
        this.municipioDAO = municipioDAO;
    }

    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, Boolean familiaNumerosa, Boolean activo) {
        return alumnoDAO.buscarAlumnos(nombre, apellido, familiaNumerosa, activo);
    }
/*
    public int insertarAlumno(AlumnoDTO alumno) {
        return alumnoDAO.insertarAlumno(alumno);
    }
    */
    public ArrayList<DesplegableDTO> obtenerDesplegableMunicipios() {
        return municipioDAO.listarParaDesplegable();
    }


    public int actualizarAlumno(AlumnoDTO alumno) {
        return alumnoDAO.actualizarAlumno(alumno);
    }

    public void insertarAlumno(AlumnoDTO alumno) {
        if (alumno.getId() == null) {
            throw new ReglaNegocioException("El ID del alumno es obligatorio.");
        }
        if (alumno.getNombre() == null ||
                alumno.getNombre().isBlank()) {
            throw new ReglaNegocioException("El nombre del alumno es obligatorio.");
        }
        if (alumnoDAO.obtenerAlumnoPorId(alumno.getId()) != null) {
            throw new ReglaNegocioException("Ya existe un alumno con el ID " + alumno.getId() + ".");
        }
        int filasAfectadas = alumnoDAO.insertarAlumno(alumno);
        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido insertar el alumno por un error inesperado. Inténtalo de nuevo.");
        }
    }


    public AlumnoDTO insertarAlumnoRest(AlumnoDTO alumno) {
        if (alumnoDAO.buscarPorId(alumno.getId()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un alumno con id "
                            + alumno.getId());
        }
        int filasAfectadas = alumnoDAO.insertarAlumno(alumno);
        if (filasAfectadas != 1) {
            throw new
                    ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo insertar el alumno");
        }
        return alumnoDAO.buscarPorId(alumno.getId());
    }
}
    public int eliminarAlumno(Integer id) {
        return alumnoDAO.eliminarAlumno(id);
    }

    public ArrayList<AlumnoDTO> filtrarAlumnos(String nombre, String
                                                       apellido, Integer idMunicipio,
                                               Boolean familiaNumerosa,
                                               Boolean activo) {
        return alumnoDAO.filtrarAlumnos(nombre, apellido, idMunicipio,
                familiaNumerosa, activo);
    }

