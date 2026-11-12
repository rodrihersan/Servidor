package com.example.bibliotecavideojuegos.dao.impl;

import com.example.bibliotecavideojuegos.dao.interfaz.IPrestamoDAO;
import com.example.bibliotecavideojuegos.dto.InformeVideojuegoDTO;
import com.example.bibliotecavideojuegos.dto.PrestamoDTO;
import com.example.bibliotecavideojuegos.repository.PrestamoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoDAO implements IPrestamoDAO {

    private final PrestamoRepository prestamoRepository;

    public PrestamoDAO(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public int contarPrestamosAbiertos(int idSocio) {
        return prestamoRepository.contarPrestamosAbiertos(idSocio);
    }

    @Override
    public boolean tienePrestamosVencidos(int idSocio) {
        return prestamoRepository.tienePrestamosVencidos(idSocio);
    }

    @Override
    public int insertarPrestamo(PrestamoDTO prestamo) {
        return prestamoRepository.insertarPrestamo(prestamo);
    }

    @Override
    public ArrayList<PrestamoDTO> listarPrestamos(String socio, String videojuego, String estado, LocalDate fechaDesde) {
        return prestamoRepository.listarPrestamos(socio, videojuego, estado);
    }

    @Override
    public PrestamoDTO obtenerPrestamoPorId(int idPrestamo) {
        return prestamoRepository.obtenerPrestamoPorId(idPrestamo);
    }

    @Override
    public int devolverPrestamo(int idPrestamo, LocalDate fechaDevolucion, double multa, boolean danada) {
        return prestamoRepository.devolverPrestamo(idPrestamo, fechaDevolucion, multa, danada);
    }

    @Override
    public int renovarPrestamo(int idPrestamo, LocalDate nuevaFechaLimite) {
        return prestamoRepository.renovarPrestamo(idPrestamo, nuevaFechaLimite);
    }

    @Override
    public ArrayList<InformeVideojuegoDTO> listarVideojuegosMasPrestados() {
        return prestamoRepository.listarVideojuegosMasPrestados();
    }
}