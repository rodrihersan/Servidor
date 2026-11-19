package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;

import java.util.ArrayList;

public interface IVideojuegoDAO {
    ArrayList<VideojuegoDTO> buscar(VideojuegoDTO filtro);
    VideojuegoDTO buscarPorId(int id);
    int insertar(VideojuegoDTO videojuego);
    void actualizar(VideojuegoDTO videojuego);
    void borrar(int id);
    int contarCopias(int idVideojuego);
    ArrayList<VideojuegoDTO> masPrestados();
}
