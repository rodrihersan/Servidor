
package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.ICopiaDAO;
import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.repository.CopiaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CopiaDAO implements ICopiaDAO {

    private final CopiaRepository copiaRepository;

    public CopiaDAO(CopiaRepository copiaRepository) {
        this.copiaRepository = copiaRepository;
    }

    @Override
    public ArrayList<DesplegableDTO> listaCopiasDisponibles() {
        return copiaRepository.listaCopiasDisponibles();
    }

    @Override
    public boolean esCopiaDisponible(int idCopia) {
        return copiaRepository.esCopiaDisponible(idCopia);
    }

    @Override
    public Integer obtenerPegi(int idCopia) {
        return copiaRepository.obtenerPegi(idCopia);
    }

    @Override
    public Integer obtenerAnioLanzamiento(int idCopia) {
        return copiaRepository.obtenerAnioLanzamiento(idCopia);
    }
}

