package com.aagpazos.bibliotecavideojuegospractica.dao.impl;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IDesarrolladoraDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class DesarrolladoraDAO implements IDesarrolladoraDAO {
    private final DesarrolladoraRepository desarrolladoraRepository;

    public DesarrolladoraDAO(DesarrolladoraRepository desarrolladoraRepository) {
        this.desarrolladoraRepository = desarrolladoraRepository;
    }


    @Override
    public ArrayList<DesarrolladoraDTO> listarDesarrolladoras() {
        return desarrolladoraRepository.listarDesarrolladoras();
    }

    @Override
    public boolean existeDesarrolladora(int id) {
        return desarrolladoraRepository.existeDesarrolladora(id);
    }
}
