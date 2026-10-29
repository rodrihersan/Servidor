package com.dawes.hernandezsanchezrodrigo_biblioteca.repository;

import com.dawes.hernandezsanchezrodrigo_biblioteca.dto.VideojuegoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class VideojuegoRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);
    private final DataSource dataSource;

    public VideojuegoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<VideojuegoDTO> buscarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, boolean soloDisponibles) {
        String sql = "SELECT id, titulo, anio_lanzamiento, pegi, id_desarrolladora, desarrolladora, copias_totales, copias_disponibles "
                + "FROM v_videojuegos_detalle "
                + "WHERE titulo LIKE ? "
                + "AND (? IS NULL OR id_desarrolladora = ?) "
                + "AND (? IS NULL OR anio_lanzamiento >= ?) "
                + "AND (? IS NULL OR pegi <= ?) "
                + "AND (? = false OR copias_disponibles > 0) "
                + "ORDER BY titulo";
        ArrayList<VideojuegoDTO> listaVideojuegos = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + titulo + "%");
            ps.setObject(2, idDesarrolladora);
            ps.setObject(3, idDesarrolladora);
            ps.setObject(4, anioDesde);
            ps.setObject(5, anioDesde);
            ps.setObject(6, pegiMaximo);
            ps.setObject(7, pegiMaximo);
            ps.setBoolean(8, soloDisponibles);

            logger.info("Query a ejecutar: {}", ps);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaVideojuegos.add(mapearVideojuego(rs));
            }
            connection.close();

        } catch (SQLException e) {
            logger.error("Error al buscar alumnos", e);
            cerrarConexion(connection);
        }
        return listaVideojuegos;
    }

    private VideojuegoDTO mapearVideojuego(ResultSet rs) throws SQLException {
        return new VideojuegoDTO(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getInt("anio_lanzamiento"),
                rs.getInt("pegi"),
                rs.getInt("id_desarrolladora"),
                rs.getString("desarrolladora"),
                rs.getInt("copias_totales"),
                rs.getInt("copias_disponibles")
        );
    }



    private void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
