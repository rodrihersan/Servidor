package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.impl.DesarrolladoraDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IDesarrolladoraDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DesarrolladoraService {
    private IDesarrolladoraDAO desarrolladoraDAO;

    public DesarrolladoraService(IDesarrolladoraDAO desarrolladoraDAO) {
        this.desarrolladoraDAO = desarrolladoraDAO;
    }

    public ArrayList<DesarrolladoraDTO> listarDesarrolladoras() {
        return  desarrolladoraDAO.listarDesarrolladoras();
    }


}
