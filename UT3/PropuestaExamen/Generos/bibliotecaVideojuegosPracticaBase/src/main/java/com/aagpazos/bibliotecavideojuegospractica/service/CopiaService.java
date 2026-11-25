package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ICopiasDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IVideojuegoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class CopiaService {
    private static final Set<String> ESTADOS = Set.of("NUEVO", "BUENO", "DESGASTADO", "DANADO");

    private final ICopiasDAO copiaDAO;
    private final IVideojuegoDAO videojuegoDAO;

    public CopiaService(ICopiasDAO copiaDAO, IVideojuegoDAO videojuegoDAO) {
        this.copiaDAO = copiaDAO;
        this.videojuegoDAO = videojuegoDAO;
    }

    public ArrayList<CopiaDTO> listarDisponbles() {
        return copiaDAO.listarDisponibles();
    }

    public CopiaDTO obtener(int id) {
        CopiaDTO copia = copiaDAO.buscarPorId(id);
        if (copia == null) {
            throw new RecursoNoEncontradoException("No existe la copia con id " + id);
        }
        return copia;
    }

    public ArrayList<CopiaDTO> listarDisponiblesPorVideojuego(int idVideojuego) {
        VideojuegoDTO videojuego = videojuegoDAO.buscarPorId(idVideojuego);

        if (videojuego == null) {
            throw new RecursoNoEncontradoException("No existe el videojuego con id " + idVideojuego);
        }

        return copiaDAO.listarDisponiblesPorVideojuego(idVideojuego);
    }

    public int insertar(CopiaDTO copia) {

        if (copia.getCodigoInventario() == null || copia.getCodigoInventario().isBlank()) {
            throw new DatosInvalidosException("El código de inventario es obligatorio");
        }

        if (copia.getIdVideojuego() == null || videojuegoDAO.buscarPorId(copia.getIdVideojuego()) == null) {
            throw new RecursoNoEncontradoException("No existe el videojuego indicado");
        }

        if (copia.getIdPlataforma() == null) {
            throw new DatosInvalidosException("La plataforma es obligatoria");
        }

        if (copia.getEstado() == null || !ESTADOS.contains(copia.getEstado())) {
            throw new DatosInvalidosException("El estado de la copia no es válido");
        }

        copia.setActiva(true);

        return copiaDAO.insertar(copia);
    }

    public void actualizarEstado(int id, String estado) {

        CopiaDTO copia = copiaDAO.buscarPorId(id);

        if (copia == null) {
            throw new RecursoNoEncontradoException("No existe la copia con id " + id);
        }

        if (estado == null || !ESTADOS.contains(estado)) {
            throw new DatosInvalidosException("El estado no es válido");
        }

        copiaDAO.actualizarEstado(id, estado);
    }

    public void borrar(int id) {

        CopiaDTO copia = copiaDAO.buscarPorId(id);

        if (copia == null) {
            throw new RecursoNoEncontradoException("No existe la copia con id " + id);
        }

        copiaDAO.borrar(id);
    }
}

