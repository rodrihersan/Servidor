package com.example.bibliotecavideojuegos.repository;

import com.example.bibliotecavideojuegos.dto.VideojuegoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


@Repository
public class VideojuegoRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);
    private final DataSource dataSource;

    public VideojuegoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<VideojuegoDTO> filtrarVideojuegos(String titulo, Integer idDesarrolladora, Integer anioDesde, Integer pegiMaximo, Boolean soloDisponibles) {
        StringBuilder sql = new StringBuilder("SELECT " + "id, titulo, anio_lanzamiento, pegi, id_desarrolladora, "
                + "desarrolladora, copias_totales, copias_disponibles "
                + "FROM v_videojuegos_detalle "
                + "WHERE 1 = 1");

        ArrayList<Object> parametros = new ArrayList<>();

        if (titulo != null && !titulo.isBlank()) {
            sql.append(" AND titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (idDesarrolladora != null) {
            sql.append(" AND id_desarrolladora = ?");
            parametros.add(idDesarrolladora);
        }
        if (anioDesde != null) {
            sql.append(" AND anio_lanzamiento >= ?");
            parametros.add(anioDesde);
        }
        if (pegiMaximo != null) {
            sql.append(" AND pegi <= ?");
            parametros.add(pegiMaximo);
        }
        if (soloDisponibles != null && soloDisponibles) {
            sql.append(" AND copias_disponibles > 0");
        }
        sql.append(" ORDER BY titulo");

        ArrayList<VideojuegoDTO> listaVideojuegos = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString());

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            logger.debug("Query a ejecutar: {}", ps);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaVideojuegos.add(mapearVideojuego(rs));
            }
            connection.close();

        } catch (SQLException e) {
            logger.error("Error al filtrar videojuegos", e);
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

    public int insertar(VideojuegoDTO videojuego) {
        String sql = "INSERT INTO videojuegos (titulo, anio_lanzamiento, pegi, id_desarrolladora) " + "VALUES (?, ?, ?, ?)";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, videojuego.getTitulo());
            ps.setInt(2, videojuego.getAnioLanzamiento());
            ps.setInt(3, videojuego.getPegi());
            ps.setInt(4, videojuego.getIdDesarrolladora());

            filasAfectadas = ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al insertar el videojuego {}", videojuego.getTitulo(), e);
            cerrarConexion(connection);
        }
        return filasAfectadas;
    }
}
