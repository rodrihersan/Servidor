package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.DesarrolladoraRepository;

import java.util.ArrayList;

public interface IDesarrolladoraDAO {
    ArrayList<DesarrolladoraDTO> listarDesarrolladoras();
    boolean existeDesarrolladora(int id);
}
