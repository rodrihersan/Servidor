package com.example.bibliotecavideojuegos.dao.interfaz;

import com.example.bibliotecavideojuegos.dto.VideojuegoDTO;

import java.util.ArrayList;

public interface IVideojuegoDAO {
    ArrayList<VideojuegoDTO> filtrarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles);

    int insertarVideojuego(VideojuegoDTO videojuego);
}


