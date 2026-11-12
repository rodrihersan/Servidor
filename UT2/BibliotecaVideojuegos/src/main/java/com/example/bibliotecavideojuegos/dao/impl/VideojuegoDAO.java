package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.IVideojuegoDAO;
import com.example.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.example.bibliotecavideojuegos.repository.VideojuegoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class VideojuegoDAO implements IVideojuegoDAO {
    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoDAO(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    @Override
    public ArrayList<VideojuegoDTO> filtrarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles) {
        return videojuegoRepository.filtrarVideojuegos(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
    }

    @Override
    public int insertarVideojuego(VideojuegoDTO videojuego) {
        return videojuegoRepository.insertar(videojuego);
    }
}