package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ICopiasDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IPrestamoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ISocioDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IVideojuegoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.*;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Set;

@Service
public class PrestamoService {
    final int MAX_PRESTAMOS_ABIERTOS = 3;
    final int PLAZO_NOVEDAD_DIAS = 3;
    final int PLAZO_NORMAL_DIAS = 7;
    final double MULTA_POR_DIA = 0.50;
    private static final Set<String> ESTADOS = Set.of("EN_PLAZO", "VENCIDO", "DEVUELTO", "ABIERTO");

    private final IPrestamoDAO prestamoDAO;
    private final ICopiasDAO copiaDAO;
    private final ISocioDAO socioDAO;
    private final IVideojuegoDAO videojuegoDAO;

    public PrestamoService(IPrestamoDAO prestamoDAO, ICopiasDAO copiaDAO, ISocioDAO socioDAO, IVideojuegoDAO videojuegoDAO) {
        this.prestamoDAO = prestamoDAO;
        this.copiaDAO = copiaDAO;
        this.socioDAO = socioDAO;
        this.videojuegoDAO = videojuegoDAO;
    }

    public ArrayList<PrestamoDTO> listar(PrestamoDTO filtro) {
        String estado = null;
        if (filtro.getEstado() != null && !filtro.getEstado().isBlank() && !filtro.getEstado().equalsIgnoreCase("TODOS")) {
            estado = filtro.getEstado().trim().toUpperCase();
            if (!ESTADOS.contains(estado)) {
                throw new DatosInvalidosException("El estado debe ser EN_PLAZO, VENCIDO, DEVUELTO, ABIERTO o TODOS");
            }
        }
        return prestamoDAO.buscar(new PrestamoDTO(filtro.getSocio(), filtro.getTitulo(), estado, filtro.getFechaPrestamo()));
    }

    public PrestamoDTO obtener(int id) {
        PrestamoDTO prestamo = prestamoDAO.buscarPorId(id);
        if (prestamo == null) {
            throw new RecursoNoEncontradoException("No existe el préstamo con id " + id);
        }
        return prestamo;
    }

    public PrestamoDTO registrar(PrestamoDTO req) {
        if (req == null || req.getIdSocio() == null || req.getIdCopia() == null) {
            throw new DatosInvalidosException("idSocio e idCopia son obligatorios");
        }
        SocioDTO socio = socioDAO.buscarPorId(req.getIdSocio());
        if (socio == null) {
            throw new RecursoNoEncontradoException("No existe el socio con id " + req.getIdSocio());
        }
        CopiaDTO copia = copiaDAO.buscarPorId(req.getIdCopia());
        if (copia == null) {
            throw new RecursoNoEncontradoException("No existe la copia con id " + req.getIdCopia());
        }
        VideojuegoDTO juego = videojuegoDAO.buscarPorId(copia.getIdVideojuego());
        LocalDate hoy = LocalDate.now();

        // RN-1: socio activo
        if (!socio.isActivo()) {
            throw new ReglaNegocioException("RN-1", "El socio " + socio.getNombre() + " está dado de baja");
        }
        // RN-2: copia disponible (activa y sin préstamo abierto)
        if (!copia.getDisponible()) {
            String motivo = "ya está prestada";
            if(!copia.getActiva()) {
                motivo = "no está activa";
            }
            throw new ReglaNegocioException("RN-2", "La copia " + copia.getCodigoInventario() + " no está disponible: " + motivo);
        }
        // RN-3: máximo de préstamos abiertos
        if (socio.getPrestamosAbiertos() >= MAX_PRESTAMOS_ABIERTOS) {
            throw new ReglaNegocioException("RN-3", "El socio ya tiene " + socio.getPrestamosAbiertos()
                    + " préstamos abiertos (máximo " + MAX_PRESTAMOS_ABIERTOS + ")");
        }
        // RN-4: edad mínima según PEGI
        if (socio.getEdad() < juego.getPegi()) {
            throw new ReglaNegocioException("RN-4", "El juego es PEGI " + juego.getPegi()
                    + " y el socio tiene " + socio.getEdad() + " años");
        }
        // RN-5: sin préstamos vencidos pendientes
        if (prestamoDAO.contarVencidosSinDevolver(socio.getId(), hoy) > 0) {
            throw new ReglaNegocioException("RN-5", "El socio tiene préstamos vencidos sin devolver");
        }

        int plazo = PLAZO_NORMAL_DIAS;
        if(esNovedad(juego, hoy))
            plazo = PLAZO_NOVEDAD_DIAS;
        LocalDate fechaLimite = hoy.plusDays(plazo);
        int id = prestamoDAO.insertar(socio.getId(), copia.getId(), hoy, fechaLimite);
        return obtener(id);
    }

    public PrestamoDTO devolver(int idPrestamo, Boolean copiaDañana) {
        PrestamoDTO prestamo = obtener(idPrestamo); // 404 si no existe
        if (prestamo.getFechaDevolucion() != null) {
            throw new ConflictoException("El préstamo " + idPrestamo + " ya estaba devuelto");
        }
        LocalDate hoy = LocalDate.now();
        long diasRetraso = Math.max(0, ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy));
        Double multa = MULTA_POR_DIA * diasRetraso;
        boolean copiaDanada = copiaDañana != null && copiaDañana;

        boolean devuelto = prestamoDAO.registrarDevolucion(idPrestamo, prestamo.getIdCopia(), hoy, multa, copiaDanada);
        if (!devuelto) {
            throw new ConflictoException("El préstamo " + idPrestamo + " ya estaba devuelto");
        }
        return obtener(idPrestamo);
    }

    public ResumenDTO resumen() {
        return prestamoDAO.resumen();
    }

    /** Un juego es "novedad" si es del año actual o del anterior. */
    private boolean esNovedad(VideojuegoDTO juego, LocalDate hoy) {
        return juego.getAñoLanzamiento() >= hoy.getYear() - 1;
    }


    public ArrayList<VideojuegoDTO> masPrestados(){
        return videojuegoDAO.masPrestados();
    }

    public PrestamoDTO renovar(int idPrestamo) {
        PrestamoDTO prestamo = obtener(idPrestamo); // si no existe, RecursoNoEncontradoException

        // RN-RE1: no se renueva un préstamo vencido
        if (prestamo.getFechaDevolucion() == null && prestamo.getFechaLimite().isBefore(LocalDate.now())) {
            throw new ReglaNegocioException("RN-RE1", "No se puede renovar el préstamo " + idPrestamo
                    + ": venció el " + prestamo.getFechaLimite());
        }
        // RN-RE2: máximo de renovaciones
        if (prestamo.getRenovaciones() >= 1) {
            throw new ReglaNegocioException("RN-RE2", "El préstamo " + idPrestamo
                    + " ya se ha renovado " + prestamo.getRenovaciones() + " vez (máximo " + 1 + ")");
        }

        //Calcular fecha
        LocalDate nuevaFechaLimite = prestamo.getFechaLimite().plusDays(7);

        if (!prestamoDAO.renovar(idPrestamo, nuevaFechaLimite)) {
            throw new ConflictoException("El préstamo " + idPrestamo + " no se puede renovar");
        }
        return obtener(idPrestamo);
    }

}
