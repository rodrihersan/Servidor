package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ICopiasDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IVideojuegoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;
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
}

