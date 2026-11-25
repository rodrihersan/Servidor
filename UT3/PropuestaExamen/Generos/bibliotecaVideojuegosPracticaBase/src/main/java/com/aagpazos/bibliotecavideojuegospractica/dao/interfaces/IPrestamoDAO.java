package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.ResumenDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IPrestamoDAO {
    ArrayList<PrestamoDTO> buscar(PrestamoDTO filtro);
    PrestamoDTO buscarPorId(int id);
    int insertar(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite);
    int contarVencidosSinDevolver(int idSocio, LocalDate hoy);
    boolean registrarDevolucion(int idPrestamo, int idCopia, LocalDate fecha, Double multa, boolean copiaDanada);
    ResumenDTO resumen();
    boolean renovar(int idPrestamo, LocalDate nuevaFechaLimite);
}
