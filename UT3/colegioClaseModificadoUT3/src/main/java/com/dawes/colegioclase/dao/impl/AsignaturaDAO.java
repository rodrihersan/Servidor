package com.dawes.colegioclase.dao.impl;

import com.dawes.colegioclase.dao.interfaz.IAsignaturaDAO;
import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.dto.DesplegableDTO;
import com.dawes.colegioclase.repository.AlumnoRepository;
import com.dawes.colegioclase.repository.AsignaturaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AsignaturaDAO implements IAsignaturaDAO {
    private final AsignaturaRepository asignaturaRepository;
    public AsignaturaDAO(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    @Override
    public double obtenerTasaAsignatura(Integer idAsignatura) {
        return asignaturaRepository.obtenerTasaAsignatura(idAsignatura);
    }

    @Override
    public ArrayList<DesplegableDTO> listarAsignaturasParaDesplegable() {
        return asignaturaRepository.listarAsignaturasParaDesplegable();
    }
}
