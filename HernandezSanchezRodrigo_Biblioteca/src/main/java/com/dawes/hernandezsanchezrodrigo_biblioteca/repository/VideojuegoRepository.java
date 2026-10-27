package com.dawes.hernandezsanchezrodrigo_biblioteca.repository;

import com.dawes.hernandezsanchezrodrigo_biblioteca.dto.VideojuegosDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;

@Repository
public class VideojuegoRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);
    private final DataSource dataSource;

    public VideojuegoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<VideojuegosDTO> buscarVideojuego(Integer id, String
         titulo, Integer anioLanzamiento, Integer pegi, Integer idDesarrolladora, String desarrolladora,
                                                      Integer copiasTotales, Integer copiasDisponibles,
                                                      boolean disponible) {
        String sql = "SELECT v.titulo, d.titulo, v.apellidos, v.id_municipio, m.nombre AS municipio, "
                + "a.familia_numerosa, a.activo "
                + "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio "
                + "WHERE a.nombre LIKE ? AND a.apellidos LIKE ? "
                + "AND a.familia_numerosa = ? AND a.activo = ?";
        ArrayList<VideojuegosDTO> listaVideojuegos = new ArrayList<>();
        Connection connection = null;

    }
}

