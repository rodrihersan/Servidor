package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.IDesarrolladoraDAO;
import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class DesarrolladoraDAO implements IDesarrolladoraDAO {
    private final DesarrolladoraRepository desarrolladoraRepository;

    public DesarrolladoraDAO(DesarrolladoraRepository desarrolladoraRepository) {
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    @Override
    public ArrayList<DesplegableDTO> listaDesplegable() {
        return desarrolladoraRepository.listaDesplegable();
    }
}
