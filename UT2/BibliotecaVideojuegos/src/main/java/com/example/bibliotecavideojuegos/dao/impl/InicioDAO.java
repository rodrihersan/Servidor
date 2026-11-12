package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.IInicioDAO;
import com.example.bibliotecavideojuegos.repository.InicioRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InicioDAO implements IInicioDAO {

    private final InicioRepository inicioRepository;

    public InicioDAO(InicioRepository inicioRepository) {
        this.inicioRepository = inicioRepository;
    }

    @Override
    public int contarVideojuegos() {
        return inicioRepository.contarVideojuegos();
    }

    @Override
    public int contarSociosActivos() {
        return inicioRepository.contarSociosActivos();
    }

    @Override
    public int contarPrestamosAbiertos() {
        return inicioRepository.contarPrestamosAbiertos();
    }

    @Override
    public int contarPrestamosVencidos() {
        return inicioRepository.contarPrestamosVencidos();
    }
}