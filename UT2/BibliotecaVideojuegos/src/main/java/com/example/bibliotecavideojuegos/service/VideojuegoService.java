package com.example.bibliotecavideojuegos.service;

import com.example.bibliotecavideojuegos.dao.interfaz.IDesarrolladoraDAO;
import com.example.bibliotecavideojuegos.dao.interfaz.IVideojuegoDAO;
import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class VideojuegoService {
    private final IVideojuegoDAO videojuegoDAO;
    private final IDesarrolladoraDAO desarrolladoraDAO;

    public VideojuegoService(IVideojuegoDAO videojuegoDAO, IDesarrolladoraDAO desarrolladoraDAO) {
        this.videojuegoDAO = videojuegoDAO;
        this.desarrolladoraDAO = desarrolladoraDAO;
    }

    public ArrayList<VideojuegoDTO> filtrarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles) {
        return videojuegoDAO.filtrarVideojuegos(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableDesarrolladoras() {
        return desarrolladoraDAO.listaDesplegable();
    }

    public void insertarVideojuego(VideojuegoDTO videojuego) {
        int anioActual = LocalDate.now().getYear();
        Integer pegi = videojuego.getPegi();

        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()) {
            throw new ReglaNegocioException("El título del videojuego es obligatorio.");
        }
        if (videojuego.getAnioLanzamiento() == null || videojuego.getAnioLanzamiento() < 1970 || videojuego.getAnioLanzamiento() > anioActual) {
            throw new ReglaNegocioException("El año de lanzamiento debe estar entre 1970 y " + anioActual + ".");
        }
        if (pegi == null || (pegi != 3 && pegi != 7 && pegi != 12 && pegi != 16 && pegi != 18)) {
            throw new ReglaNegocioException("El PEGI debe ser 3, 7, 12, 16 o 18.");
        }
        if (videojuego.getIdDesarrolladora() == null) {
            throw new ReglaNegocioException("Debes elegir una desarrolladora.");
        }

        int filasAfectadas = videojuegoDAO.insertarVideojuego(videojuego);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioException("No se ha podido insertar el videojuego por un error inesperado. Inténtalo de nuevo.");
        }
    }
}


