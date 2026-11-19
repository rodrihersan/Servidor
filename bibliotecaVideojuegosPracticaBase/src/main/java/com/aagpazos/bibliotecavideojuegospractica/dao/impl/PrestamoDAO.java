package com.aagpazos.bibliotecavideojuegospractica.dao.impl;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IPrestamoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.ResumenDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.PrestamoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoDAO implements IPrestamoDAO {
    private final PrestamoRepository prestamoRepository;

    public PrestamoDAO(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public ArrayList<PrestamoDTO> buscar(PrestamoDTO filtro) {
        return prestamoRepository.buscar(filtro);
    }

    @Override
    public PrestamoDTO buscarPorId(int id) {
        return prestamoRepository.buscarPorId(id);
    }

    @Override
    public int insertar(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        return prestamoRepository.insertar(idSocio, idCopia, fechaPrestamo, fechaLimite);
    }

    @Override
    public int contarVencidosSinDevolver(int idSocio, LocalDate hoy) {
        return prestamoRepository.contarVencidosSinDevolver(idSocio, hoy);
    }

    @Override
    public boolean registrarDevolucion(int idPrestamo, int idCopia, LocalDate fecha, Double multa, boolean copiaDanada) {
        return prestamoRepository.registrarDevolucion(idPrestamo, idCopia, fecha, multa, copiaDanada);
    }

    @Override
    public ResumenDTO resumen() {
        return prestamoRepository.resumen();
    }

    @Override
    public boolean renovar(int idPrestamo, LocalDate nuevaFechaLimite) {
        return prestamoRepository.renovar(idPrestamo, nuevaFechaLimite);
    }

}
