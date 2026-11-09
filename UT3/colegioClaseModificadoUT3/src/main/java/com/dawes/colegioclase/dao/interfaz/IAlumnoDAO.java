package com.dawes.colegioclase.dao.interfaz;


import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.dto.DesplegableDTO;

import java.util.ArrayList;

public interface IAlumnoDAO {
    ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, boolean familiaNumerosa, boolean activo);
    int insertarAlumno(AlumnoDTO alumno);
    int actualizarAlumno(AlumnoDTO alumno);
    int eliminarAlumno(Integer id);
    ArrayList<AlumnoDTO> filtrarAlumnos(String nombre, String apellido, Integer idMunicipio, Boolean familiaNumerosa, Boolean activo);
    AlumnoDTO obtenerAlumnoPorId(Integer id);
    boolean esFamiliaNumerosa(Integer idAlumno);
    ArrayList<DesplegableDTO> listarAlumnosParaDesplegable();
}
