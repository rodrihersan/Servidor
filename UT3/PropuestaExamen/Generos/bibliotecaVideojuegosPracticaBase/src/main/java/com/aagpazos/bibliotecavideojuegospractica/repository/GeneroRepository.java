
package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.GeneroDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.AccesoDatosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class GeneroRepository {

    private static final Logger logger = LoggerFactory.getLogger(GeneroRepository.class);

    private final DataSource dataSource;

    public GeneroRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<GeneroDTO> listarGeneros() {

        String sql = "SELECT id, nombre FROM generos ORDER BY nombre";
        ArrayList<GeneroDTO> lista = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new GeneroDTO(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            logger.error("Error al listar géneros", e);
            throw new AccesoDatosException("Error al listar géneros", e);
        }

        return lista;
    }


    public GeneroDTO buscarPorId(int id) {

        String sql = "SELECT id, nombre FROM generos WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new GeneroDTO(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }

        } catch (SQLException e) {
            logger.error("Error al buscar el género con id {}", id, e);
            throw new AccesoDatosException("Error al buscar un género", e);
        }

        return null;
    }


    public int insertar(GeneroDTO genero) {

        String sql = "INSERT INTO generos (nombre) VALUES (?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, genero.getNombre());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("No se pudo obtener el ID del género insertado");

        } catch (SQLException e) {
            logger.error("Error al insertar un género", e);
            throw new AccesoDatosException("Error al insertar un género", e);
        }
    }


    public void actualizar(int id, String nombre) {

        String sql = "UPDATE generos SET nombre = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error al actualizar el género con id {}", id, e);
            throw new AccesoDatosException("Error al actualizar un género", e);
        }
    }


    public void borrar(int id) {

        String sql = "DELETE FROM generos WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error al eliminar el género con id {}", id, e);
            throw new AccesoDatosException("Error al eliminar un género", e);
        }
    }

}
