package com.aagpazos.bibliotecavideojuegospractica.dao.impl;
import java.util.ArrayList;
import java.util.List;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IVideojuegoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.VideojuegoRepository;
import org.springframework.stereotype.Repository;


@Repository
public class VideojuegoDAO implements IVideojuegoDAO {
    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoDAO(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    @Override
    public ArrayList<VideojuegoDTO> buscar(VideojuegoDTO filtro) {
        return videojuegoRepository.buscar(filtro);
    }

    @Override
    public VideojuegoDTO buscarPorId(int id) {
        return videojuegoRepository.buscarPorId(id);
    }

    @Override
    public int insertar(VideojuegoDTO videojuego) {
        return videojuegoRepository.insertar(videojuego);
    }

    @Override
    public void actualizar(VideojuegoDTO videojuego) {
        videojuegoRepository.actualizar(videojuego);
    }

    @Override
    public void borrar(int id) {
        videojuegoRepository.borrar(id);
    }

    @Override
    public int contarCopias(int idVideojuego) {
        return videojuegoRepository.contarCopias(idVideojuego);
    }

    @Override
    public ArrayList<VideojuegoDTO> masPrestados() {
        return videojuegoRepository.masPrestados();
    }
}
