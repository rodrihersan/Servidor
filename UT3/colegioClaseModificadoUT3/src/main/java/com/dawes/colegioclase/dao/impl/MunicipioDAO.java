package com.dawes.colegioclase.dao.impl;

import com.dawes.colegioclase.dao.interfaz.IMunicipioDAO;
import com.dawes.colegioclase.dto.DesplegableDTO;
import com.dawes.colegioclase.repository.MunicipioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MunicipioDAO implements IMunicipioDAO {
    private final MunicipioRepository municipioRepository;

    public MunicipioDAO(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    @Override
    public ArrayList<DesplegableDTO> listarParaDesplegable() {
        return municipioRepository.listarParaDesplegable();
    }
}
