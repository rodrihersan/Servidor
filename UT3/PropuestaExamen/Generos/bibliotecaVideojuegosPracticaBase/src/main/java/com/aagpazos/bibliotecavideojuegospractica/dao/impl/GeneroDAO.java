
package com.aagpazos.bibliotecavideojuegospractica.dao.impl;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IGeneroDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.GeneroDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.GeneroRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class GeneroDAO implements IGeneroDAO {

    private final GeneroRepository generoRepository;

    public GeneroDAO(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public ArrayList<GeneroDTO> listarGeneros() {
        return generoRepository.listarGeneros();
    }

    @Override
    public GeneroDTO buscarPorId(int id) {
        return generoRepository.buscarPorId(id);
    }

    @Override
    public int insertar(GeneroDTO genero) {
        return generoRepository.insertar(genero);
    }

    @Override
    public void actualizar(int id, String nombre) {
        generoRepository.actualizar(id, nombre);
    }

    @Override
    public void borrar(int id) {
        generoRepository.borrar(id);
    }
}


