package com.dawes.colegio.dao.impl;

import com.dawes.colegio.dao.interfaz.IMunicipioDAO;
import com.dawes.colegio.dto.DesplegableDTO;
import com.dawes.colegio.repository.MunicipioRepository;
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
