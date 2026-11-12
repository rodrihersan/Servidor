package com.example.bibliotecavideojuegos.service;

import com.example.bibliotecavideojuegos.dao.interfaz.ICopiaDAO;
import com.example.bibliotecavideojuegos.dao.interfaz.IPrestamoDAO;
import com.example.bibliotecavideojuegos.dao.interfaz.ISocioDAO;
import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.dto.InformeVideojuegoDTO;
import com.example.bibliotecavideojuegos.dto.PrestamoDTO;
import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class PrestamoService {

    private final IPrestamoDAO prestamoDAO;
    private final ISocioDAO socioDAO;
    private final ICopiaDAO copiaDAO;

    public PrestamoService(IPrestamoDAO prestamoDAO, ISocioDAO socioDAO, ICopiaDAO copiaDAO) {
        this.prestamoDAO = prestamoDAO;
        this.socioDAO = socioDAO;
        this.copiaDAO = copiaDAO;
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableSocios() {
        return socioDAO.listaSociosActivos();
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableCopias() {
        return copiaDAO.listaCopiasDisponibles();
    }

    public ArrayList<PrestamoDTO> listarPrestamos(String socio, String videojuego, String estado, LocalDate fechaDesde) {
        return prestamoDAO.listarPrestamos(socio, videojuego, estado, fechaDesde);
    }

    // Devuelve todos los préstamos abiertos (en plazo y vencidos)
    public ArrayList<PrestamoDTO> listarPrestamosAbiertos() {

        ArrayList<PrestamoDTO> todosLosPrestamos = prestamoDAO.listarPrestamos("", "", "", null);
        ArrayList<PrestamoDTO> prestamosAbiertos = new ArrayList<>();

        for (PrestamoDTO prestamo : todosLosPrestamos) {
            if (prestamo.getFechaDevolucion() == null) {
                prestamosAbiertos.add(prestamo);
            }
        }

        return prestamosAbiertos;
    }

    public LocalDate insertarPrestamo(int idSocio, int idCopia) {

        LocalDate fechaHoy = LocalDate.now();

        // RN-1: el socio debe estar activo
        if (!socioDAO.esSocioActivo(idSocio)) {
            throw new ReglaNegocioException("RN-1: El socio no está activo.");
        }

        // RN-2: la copia debe estar activa y sin préstamo abierto
        if (!copiaDAO.esCopiaDisponible(idCopia)) {
            throw new ReglaNegocioException("RN-2: La copia no está disponible.");
        }

        // RN-3: máximo 3 préstamos abiertos a la vez
        if (prestamoDAO.contarPrestamosAbiertos(idSocio) >= 3) {
            throw new ReglaNegocioException("RN-3: El socio ya tiene 3 préstamos abiertos.");
        }

        LocalDate fechaNacimiento = socioDAO.obtenerFechaNacimiento(idSocio);
        Integer pegi = copiaDAO.obtenerPegi(idCopia);
        Integer anioLanzamiento = copiaDAO.obtenerAnioLanzamiento(idCopia);

        if (fechaNacimiento == null || pegi == null || anioLanzamiento == null) {
            throw new ReglaNegocioException("No se han encontrado los datos del socio o de la copia.");
        }

        // RN-4: la edad del socio debe ser mayor o igual que el PEGI
        int edadSocio = Period.between(fechaNacimiento, fechaHoy).getYears();

        if (edadSocio < pegi) {
            throw new ReglaNegocioException("RN-4: El socio tiene " + edadSocio + " años y el juego es PEGI " + pegi + ".");
        }

        // RN-5: no puede tener préstamos vencidos sin devolver
        if (prestamoDAO.tienePrestamosVencidos(idSocio)) {
            throw new ReglaNegocioException("RN-5: El socio tiene préstamos vencidos sin devolver.");
        }

        // Novedad (año actual o anterior): 7 días. Resto: 15 días
        int diasPrestamo = 15;

        if (anioLanzamiento >= fechaHoy.getYear() - 1) {
            diasPrestamo = 7;
        }

        LocalDate fechaLimite = fechaHoy.plusDays(diasPrestamo);

        PrestamoDTO prestamo = new PrestamoDTO(null, idSocio, idCopia, fechaHoy, fechaLimite, null, 0.0);

        int filasAfectadas = prestamoDAO.insertarPrestamo(prestamo);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido registrar el préstamo por un error inesperado. Inténtalo de nuevo.");
        }

        return fechaLimite;
    }

    // RENOVAR PRÉSTAMO

    public LocalDate renovarPrestamo(int idPrestamo) {

        PrestamoDTO prestamo = prestamoDAO.obtenerPrestamoPorId(idPrestamo);

        if (prestamo == null) {
            throw new ReglaNegocioException("No se ha encontrado el préstamo. No se ha renovado.");
        }

        // RN-R1: primero comprobamos si está devuelto o vencido.
        if (prestamo.getFechaDevolucion() != null) {
            throw new ReglaNegocioException("RN-R1: El préstamo ya está devuelto. No se ha renovado.");
        }

        if (prestamo.getFechaLimite().isBefore(LocalDate.now())) {
            throw new ReglaNegocioException("RN-R1: El préstamo está vencido. No se ha renovado.");
        }

        Integer anioLanzamiento = copiaDAO.obtenerAnioLanzamiento(prestamo.getIdCopia());

        if (anioLanzamiento == null) {
            throw new ReglaNegocioException("No se ha encontrado el año de lanzamiento. No se ha renovado.");
        }

        // Calculamos el plazo que se aplicó al registrar el préstamo.
        int diasPrestamo = 15;

        if (anioLanzamiento >= prestamo.getFechaPrestamo().getYear() - 1) {
            diasPrestamo = 7;
        }

        // Calculamos la fecha límite original.
        LocalDate fechaLimiteOriginal = prestamo.getFechaPrestamo().plusDays(diasPrestamo);

        // RN-R2: si la fecha límite ya se amplió, el préstamo ya se renovó.
        if (prestamo.getFechaLimite().isAfter(fechaLimiteOriginal)) {
            throw new ReglaNegocioException("RN-R2: Este préstamo ya se ha renovado una vez. No se ha renovado de nuevo.");
        }

        LocalDate nuevaFechaLimite = prestamo.getFechaLimite().plusDays(diasPrestamo);

        int filasAfectadas = prestamoDAO.renovarPrestamo(idPrestamo, nuevaFechaLimite);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido renovar el préstamo.");
        }

        return nuevaFechaLimite;
    }

    // INFORME DE LOS CINCO VIDEOJUEGOS MÁS PRESTADOS

    public ArrayList<InformeVideojuegoDTO> obtenerCincoVideojuegosMasPrestados() {

        ArrayList<InformeVideojuegoDTO> lista = prestamoDAO.listarVideojuegosMasPrestados();
        ArrayList<InformeVideojuegoDTO> cincoPrimeros = new ArrayList<>();

        for (int i = 0; i < lista.size() && i < 5; i++) {
            cincoPrimeros.add(lista.get(i));
        }

        return cincoPrimeros;
    }

    public void devolverPrestamo(int idPrestamo, boolean danada) {

        PrestamoDTO prestamo = prestamoDAO.obtenerPrestamoPorId(idPrestamo);

        if (prestamo == null) {
            throw new ReglaNegocioException("No se ha encontrado el préstamo.");
        }

        if (prestamo.getFechaDevolucion() != null) {
            throw new ReglaNegocioException("Este préstamo ya fue devuelto.");
        }

        LocalDate fechaHoy = LocalDate.now();
        double multa = 0;

        // 0,50 € por cada día de retraso.
        if (fechaHoy.isAfter(prestamo.getFechaLimite())) {
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), fechaHoy);
            multa = diasRetraso * 0.50;
        }

        int filasAfectadas = prestamoDAO.devolverPrestamo(idPrestamo, fechaHoy, multa, danada);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido devolver el préstamo por un error inesperado. Inténtalo de nuevo.");
        }
    }
}