package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IDesarrolladoraDAO;
import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IVideojuegoDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Service
public class VideojuegoService {
    private static final Set<Integer> PEGI_VALIDOS = Set.of(3, 7, 12, 16, 18);

    private final IVideojuegoDAO videojuegoDAO;
    private final IDesarrolladoraDAO desarrolladoraDAO;

    public VideojuegoService(IVideojuegoDAO videojuegoDAO, IDesarrolladoraDAO desarrolladoraDAO) {
        this.videojuegoDAO = videojuegoDAO;
        this.desarrolladoraDAO = desarrolladoraDAO;
    }

    public ArrayList<VideojuegoDTO> listar(VideojuegoDTO filtro) {
        return videojuegoDAO.buscar(filtro);
    }

    public VideojuegoDTO obtener(int id) {
        VideojuegoDTO videojuego = videojuegoDAO.buscarPorId(id);
        if (videojuego == null) {
            throw new RecursoNoEncontradoException("No existe el videojuego con id " + id);
        }
        return videojuego;
    }

    public VideojuegoDTO crear(VideojuegoDTO req) {
        VideojuegoDTO nuevo = validar(0, req);
        int id = videojuegoDAO.insertar(nuevo);
        return obtener(id);
    }

    public VideojuegoDTO actualizar(int id, VideojuegoDTO req) {
        obtener(id); // 404 si no existe
        videojuegoDAO.actualizar(validar(id, req));
        return obtener(id);
    }

    public void borrar(int id) {
        obtener(id); // 404 si no existe
        int copias = videojuegoDAO.contarCopias(id);
        if (copias > 0) {
            throw new ConflictoException("No se puede borrar el videojuego: tiene " + copias + " copia(s) registradas");
        }
        videojuegoDAO.borrar(id);
    }

    /** Valida el cuerpo de la petición y lo convierte en un DTO listo para guardar. */
    private VideojuegoDTO validar(int id, VideojuegoDTO req) {
        if (req == null) {
            throw new DatosInvalidosException("Falta el cuerpo de la petición");
        }
        if (req.getTitulo() == null || req.getTitulo().isBlank() || req.getTitulo().length() > 100) {
            throw new DatosInvalidosException("El título es obligatorio y no puede superar los 100 caracteres");
        }
        int anioMaximo = LocalDate.now().getYear() + 2;
        if (req.getAñoLanzamiento() == null || req.getAñoLanzamiento() < 1950 || req.getAñoLanzamiento() > anioMaximo) {
            throw new DatosInvalidosException("El año de lanzamiento debe estar entre 1950 y " + anioMaximo);
        }
        if (req.getPegi() == null || !PEGI_VALIDOS.contains(req.getPegi())) {
            throw new DatosInvalidosException("El PEGI debe ser 3, 7, 12, 16 o 18");
        }
        if (req.getIdDesarrolladora() == null) {
            throw new DatosInvalidosException("La desarrolladora es obligatoria");
        }
        if (!desarrolladoraDAO.existeDesarrolladora(req.getIdDesarrolladora())) {
            throw new RecursoNoEncontradoException("No existe la desarrolladora con id " + req.getIdDesarrolladora());
        }
        return new VideojuegoDTO(id, req.getTitulo().trim(), req.getAñoLanzamiento(), req.getPegi(),
                req.getIdDesarrolladora(), null, 0, 0);
    }

    public VideojuegoDTO obtenerVideojuegoRest(int id) {
        VideojuegoDTO videojuego = videojuegoDAO.buscarPorId(id);

        if (videojuego == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el videojuego con id " + id);
        }
        return videojuego;
    }

    public VideojuegoDTO insertarVideojuegoRest(String videojuego){

    }
}


