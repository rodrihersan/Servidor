
package com.example.bibliotecavideojuegos.dao.interfaz;

import com.example.bibliotecavideojuegos.dto.DesplegableDTO;

import java.util.ArrayList;

public interface ICopiaDAO {
    ArrayList<DesplegableDTO> listaCopiasDisponibles();

    boolean esCopiaDisponible(int idCopia);

    Integer obtenerPegi(int idCopia);

    Integer obtenerAnioLanzamiento(int idCopia);
}

