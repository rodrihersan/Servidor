

package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.GeneroDTO;

import java.util.ArrayList;

public interface IGeneroDAO {

    ArrayList<GeneroDTO> listarGeneros();

    GeneroDTO buscarPorId(int id);

    public int insertar(GeneroDTO genero);

    public void actualizar(int id, String nombre);

    public void borrar(int id);
}


