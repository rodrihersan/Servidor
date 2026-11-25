
package com.aagpazos.bibliotecavideojuegospractica.service;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.IGeneroDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.GeneroDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GeneroService {

    private final IGeneroDAO generoDAO;

    public GeneroService(IGeneroDAO generoDAO) {
        this.generoDAO = generoDAO;
    }

    public ArrayList<GeneroDTO> listarGeneros() {
        return generoDAO.listarGeneros();
    }

    public GeneroDTO obtener(int id) {

        GeneroDTO genero = generoDAO.buscarPorId(id);

        if (genero == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el género con id " + id
            );
        }

        return genero;
    }

    public int insertar(GeneroDTO genero) {

        if (genero == null) {
            throw new DatosInvalidosException("Los datos del género son obligatorios");
        }

        if (genero.getNombre() == null || genero.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre del género es obligatorio");
        }

        return generoDAO.insertar(genero);
    }


    public void actualizar(int id, String nombre) {

        GeneroDTO genero = generoDAO.buscarPorId(id);

        if (genero == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el género con id " + id
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new DatosInvalidosException(
                    "El nombre del género es obligatorio"
            );
        }

        generoDAO.actualizar(id, nombre);
    }


    public void borrar(int id) {

        GeneroDTO genero = generoDAO.buscarPorId(id);

        if (genero == null) {
            throw new RecursoNoEncontradoException(
                    "No existe el género con id " + id
            );
        }

        generoDAO.borrar(id);
    }

}

