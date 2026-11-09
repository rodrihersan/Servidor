package com.dawes.colegioclase.dao.interfaz;

import com.dawes.colegioclase.dto.DesplegableDTO;

import java.util.ArrayList;

public interface IAsignaturaDAO {
    double obtenerTasaAsignatura(Integer idAsignatura);
    ArrayList<DesplegableDTO> listarAsignaturasParaDesplegable();
}
