package com.example.bibliotecavideojuegos.service;

import com.example.bibliotecavideojuegos.dao.interfaz.IInicioDAO;
import org.springframework.stereotype.Service;

@Service
public class InicioService {
    private final IInicioDAO inicioDAO;

    public InicioService(IInicioDAO inicioDAO) {
        this.inicioDAO = inicioDAO;
    }

    public int obtenerTotalVideojuegos() {
        return inicioDAO.contarVideojuegos();
    }

    public int obtenerTotalSociosActivos() {
        return inicioDAO.contarSociosActivos();
    }

    public int obtenerTotalPrestamosAbiertos() {
        return inicioDAO.contarPrestamosAbiertos();
    }

    public int obtenerTotalPrestamosVencidos() {
        return inicioDAO.contarPrestamosVencidos();
    }
}
