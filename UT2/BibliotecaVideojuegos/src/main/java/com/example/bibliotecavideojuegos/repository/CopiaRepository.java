
package com.example.bibliotecavideojuegos.repository;

import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
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
public class CopiaRepository {
    private static final Logger logger = LoggerFactory.getLogger(CopiaRepository.class);
    private final DataSource dataSource;

    public CopiaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<DesplegableDTO> listaCopiasDisponibles() {

        String sql = "SELECT c.id, " + "CONCAT(v.titulo, ' (', p.nombre, ') - ', c.codigo_inventario) AS descripcion " + "FROM copias c " +
                "JOIN videojuegos v ON c.id_videojuego = v.id " +
                "JOIN plataformas p ON c.id_plataforma = p.id " +
                "WHERE c.activa = 1 " +
                "AND NOT EXISTS (SELECT 1 FROM prestamos pr " +
                "WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL) " +
                "ORDER BY v.titulo";

        ArrayList<DesplegableDTO> listaCopias = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaCopias.add(new DesplegableDTO(
                        rs.getInt("id"),
                        rs.getString("descripcion")
                ));
            }

            connection.close();

        } catch (SQLException e) {
            logger.error("Error al recuperar las copias disponibles", e);
            cerrarConexion(connection);
        }

        return listaCopias;
    }

    public boolean esCopiaDisponible(int idCopia) {

        String sql = "SELECT c.activa FROM copias c " +
                "WHERE c.id = ? " +
                "AND NOT EXISTS (SELECT 1 FROM prestamos pr " +
                "WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL)";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idCopia);

            ResultSet rs = ps.executeQuery();

            boolean disponible = false;

            if (rs.next()) {
                disponible = rs.getBoolean("activa");
            }

            connection.close();

            return disponible;

        } catch (SQLException e) {
            logger.error("Error al comprobar si la copia está disponible", e);
            cerrarConexion(connection);
            return false;
        }
    }

    public Integer obtenerPegi(int idCopia) {

        String sql = "SELECT v.pegi " +
                "FROM copias c " +
                "JOIN videojuegos v ON c.id_videojuego = v.id " +
                "WHERE c.id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idCopia);

            ResultSet rs = ps.executeQuery();

            Integer pegi = null;

            if (rs.next()) {
                pegi = rs.getInt("pegi");
            }

            connection.close();

            return pegi;

        } catch (SQLException e) {
            logger.error("Error al obtener el PEGI del videojuego", e);
            cerrarConexion(connection);
            return null;
        }
    }

    public Integer obtenerAnioLanzamiento(int idCopia) {

        String sql = "SELECT v.anio_lanzamiento " +
                "FROM copias c " +
                "JOIN videojuegos v ON c.id_videojuego = v.id " +
                "WHERE c.id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idCopia);

            ResultSet rs = ps.executeQuery();

            Integer anioLanzamiento = null;

            if (rs.next()) {
                anioLanzamiento = rs.getInt("anio_lanzamiento");
            }

            connection.close();

            return anioLanzamiento;

        } catch (SQLException e) {
            logger.error("Error al obtener el año de lanzamiento del videojuego", e);
            cerrarConexion(connection);
            return null;
        }
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

