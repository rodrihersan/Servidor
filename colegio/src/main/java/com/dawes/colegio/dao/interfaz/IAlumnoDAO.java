package com.dawes.colegio.dao.interfaz;

import com.dawes.colegio.dto.AlumnoDTO;

import java.util.ArrayList;

public interface IAlumnoDAO {
    ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido,
                                       boolean familiaNumerosa, boolean activo);
}

