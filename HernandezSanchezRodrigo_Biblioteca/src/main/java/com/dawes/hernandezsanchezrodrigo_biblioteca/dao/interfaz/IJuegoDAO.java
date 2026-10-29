package com.dawes.hernandezsanchezrodrigo_biblioteca.dao.interfaz;

import com.dawes.hernandezsanchezrodrigo_biblioteca.dto.VideojuegoDTO;

import java.util.ArrayList;

public interface IAlumnoDAO {
    ArrayList<VideojuegoDTO> buscarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, boolean soloDisponibles);
}
