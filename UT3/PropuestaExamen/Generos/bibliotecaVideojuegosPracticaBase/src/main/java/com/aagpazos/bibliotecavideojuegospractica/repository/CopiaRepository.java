package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.AccesoDatosException;
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

    /** Copias disponibles (activas y sin préstamo abierto), para el desplegable de RF-11. */
    public ArrayList<CopiaDTO> listarDisponibles() {
        String sql = "SELECT c.id, c.codigo_inventario, c.id_videojuego, v.titulo, c.id_plataforma, pl.nombre AS plataforma, "
                        + "c.estado, c.activa, "
                        + "(c.activa = TRUE AND NOT EXISTS "
                        + "(SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL)) AS disponible "
                        + "FROM copias c "
                        + "JOIN videojuegos v ON v.id = c.id_videojuego "
                        + "JOIN plataformas pl ON pl.id = c.id_plataforma"
                        + " WHERE (c.activa = TRUE AND NOT EXISTS "
                        + "(SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL))"
                        + " ORDER BY v.titulo, pl.nombre, c.codigo_inventario";
        ArrayList<CopiaDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            logger.error("Error al listar las copias disponibles", e);
            throw new AccesoDatosException("Error al listar las copias disponibles", e);
        }
        return lista;
    }

    public int insertar(CopiaDTO copia) {
        String sql = "INSERT INTO copias " + "(codigo_inventario, id_videojuego, id_plataforma, estado, activa) " + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, copia.getCodigoInventario());
            ps.setInt(2, copia.getIdVideojuego());
            ps.setInt(3, copia.getIdPlataforma());
            ps.setString(4, copia.getEstado());
            ps.setBoolean(5, copia.getActiva());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new SQLException("No se pudo obtener el ID de la copia insertada");

        } catch (SQLException e) {
            logger.error("Error al insertar la copia", e);
            throw new AccesoDatosException("Error al insertar la copia", e);
        }
    }

    /**
     * Lista las copias disponibles de un videojuego concreto.
     */
    public ArrayList<CopiaDTO> listarDisponiblesPorVideojuego(int idVideojuego) {
        String sql = "SELECT c.id, c.codigo_inventario, c.id_videojuego, v.titulo, c.id_plataforma, pl.nombre AS plataforma, "
                + "c.estado, c.activa, "
                + "(c.activa = TRUE AND NOT EXISTS "
                + "(SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL)) AS disponible "
                + "FROM copias c "
                + "JOIN videojuegos v ON v.id = c.id_videojuego "
                + "JOIN plataformas pl ON pl.id = c.id_plataforma "
                + "WHERE c.id_videojuego = ? "
                + "AND c.activa = TRUE "
                + "AND NOT EXISTS "
                + "(SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL) "
                + "ORDER BY c.codigo_inventario";

        ArrayList<CopiaDTO> lista = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVideojuego);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error al listar las copias disponibles del videojuego {}", idVideojuego, e);
            throw new AccesoDatosException("Error al listar las copias disponibles del videojuego", e);
        }

        return lista;
    }

    /** Devuelve la copia o null si no existe. La usa PrestamoService para comprobar RN-2. */
    public CopiaDTO buscarPorId(int id) {
        String sql = "SELECT c.id, c.codigo_inventario, c.id_videojuego, v.titulo, c.id_plataforma, pl.nombre AS plataforma, "
                + "c.estado, c.activa, "
                + "(c.activa = TRUE AND NOT EXISTS "
                + "(SELECT 1 FROM prestamos pr WHERE pr.id_copia = c.id AND pr.fecha_devolucion IS NULL)) AS disponible "
                + "FROM copias c "
                + "JOIN videojuegos v ON v.id = c.id_videojuego "
                + "JOIN plataformas pl ON pl.id = c.id_plataforma"
                 + " WHERE c.id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapear(rs);
                else
                    return null;
            }
        } catch (SQLException e) {
            logger.error("Error al buscar la copia {}", id, e);
            throw new AccesoDatosException("Error al buscar la copia", e);
        }
    }

    public void actualizarEstado(int id, String estado) {

        String sql = "UPDATE copias SET estado = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error al actualizar el estado de la copia {}", id, e);
            throw new AccesoDatosException("Error al actualizar el estado de la copia", e);
        }
    }

    public void borrar(int id) {

        String sql = "DELETE FROM copias WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error al eliminar la copia {}", id, e);
            throw new AccesoDatosException("Error al eliminar la copia", e);
        }
    }

    private CopiaDTO mapear(ResultSet rs) throws SQLException {
        return new CopiaDTO(
                rs.getInt("id"),
                rs.getString("codigo_inventario"),
                rs.getInt("id_videojuego"),
                rs.getString("titulo"),
                rs.getInt("id_plataforma"),
                rs.getString("plataforma"),
                rs.getString("estado"),
                rs.getBoolean("activa"),
                rs.getBoolean("disponible"));
    }
}