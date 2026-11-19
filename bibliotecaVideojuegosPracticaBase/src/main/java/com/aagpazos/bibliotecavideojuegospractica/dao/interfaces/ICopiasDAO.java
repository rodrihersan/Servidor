package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;

import java.util.ArrayList;

public interface ICopiasDAO {
    public CopiaDTO buscarPorId(int id);
    public ArrayList<CopiaDTO> listarDisponibles();
}
