package com.example.bibliotecavideojuegos.dao.interfaz;

import com.example.bibliotecavideojuegos.dto.InformeVideojuegoDTO;
import com.example.bibliotecavideojuegos.dto.PrestamoDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IPrestamoDAO {

    int contarPrestamosAbiertos(int idSocio);

    boolean tienePrestamosVencidos(int idSocio);

    int insertarPrestamo(PrestamoDTO prestamo);

    ArrayList<PrestamoDTO> listarPrestamos(String socio, String videojuego, String estado, LocalDate fechaDesde);

    PrestamoDTO obtenerPrestamoPorId(int idPrestamo);

    int devolverPrestamo(int idPrestamo, LocalDate fechaDevolucion, double multa, boolean danada);

    int renovarPrestamo(int idPrestamo, LocalDate nuevaFechaLimite);

    ArrayList<InformeVideojuegoDTO> listarVideojuegosMasPrestados();
}