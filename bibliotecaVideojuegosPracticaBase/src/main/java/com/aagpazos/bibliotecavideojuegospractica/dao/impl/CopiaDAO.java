package com.aagpazos.bibliotecavideojuegospractica.dao.impl;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ICopiasDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.CopiaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CopiaDAO implements ICopiasDAO {
    private CopiaRepository copiaRepository;

    public CopiaDAO(CopiaRepository copiaRepository) {
        this.copiaRepository = copiaRepository;
    }

    @Override
    public CopiaDTO buscarPorId(int id) {
        return copiaRepository.buscarPorId(id);
    }

    @Override
    public ArrayList<CopiaDTO> listarDisponibles() {
        return copiaRepository.listarDisponibles();
    }
}
