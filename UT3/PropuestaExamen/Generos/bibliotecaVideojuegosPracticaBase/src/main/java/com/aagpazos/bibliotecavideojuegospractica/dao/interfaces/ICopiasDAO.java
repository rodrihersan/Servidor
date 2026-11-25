package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;

import java.util.ArrayList;

public interface ICopiasDAO {

    public CopiaDTO buscarPorId(int id);

    public ArrayList<CopiaDTO> listarDisponibles();

    public ArrayList<CopiaDTO> listarDisponiblesPorVideojuego(int idVideojuego);

    public int insertar(CopiaDTO copia);

    public void actualizarEstado(int id, String estado);

    public void borrar(int id);
}
