package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.AccesoDatosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VideojuegoRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);

    private final DataSource dataSource;

    public VideojuegoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /** SQL dinámico: solo se añaden las condiciones de los filtros que el usuario ha rellenado. Siempre con parámetros (?). */
    public ArrayList<VideojuegoDTO> buscar(VideojuegoDTO filtro) {
        String sql_base = "SELECT id, titulo, anio_lanzamiento, pegi, id_desarrolladora, desarrolladora, copias_totales, copias_disponibles "
                + "FROM v_videojuegos_detalle";
        StringBuilder sql = new StringBuilder(sql_base).append(" WHERE 1 = 1");
        ArrayList<Object> params = new ArrayList<>();

        if (filtro.getTitulo() != null && !filtro.getTitulo().isBlank()) {
            sql.append(" AND titulo LIKE ?");
            params.add("%" + filtro.getTitulo().trim() + "%");
        }
        if (filtro.getIdDesarrolladora() != null) {
            sql.append(" AND id_desarrolladora = ?");
            params.add(filtro.getIdDesarrolladora());
        }
        if (filtro.getAñoLanzamiento() != null) {
            sql.append(" AND anio_lanzamiento >= ?");
            params.add(filtro.getAñoLanzamiento());
        }
        if (filtro.getPegi() != null) {
            sql.append(" AND pegi <= ?");
            params.add(filtro.getPegi());
        }
        if (filtro.isDisponible()) {
            sql.append(" AND copias_disponibles > 0");
        }
        sql.append(" ORDER BY titulo");

        ArrayList<VideojuegoDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar videojuegos", e);
            //throw new AccesoDatosException("Error al buscar videojuegos", e);
        }
        return lista;
    }

    /** Devuelve el videojuego o null si no existe. */
    public VideojuegoDTO buscarPorId(int id) {
        String sql = "SELECT id, titulo, anio_lanzamiento, pegi, id_desarrolladora, desarrolladora, copias_totales, copias_disponibles "
                + "FROM v_videojuegos_detalle WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapear(rs);
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar el videojuego {}", id, e);
            throw new AccesoDatosException("Error al buscar el videojuego", e);
        }
    }

    public int insertar(VideojuegoDTO v) {
        String sql = "INSERT INTO videojuegos (titulo, anio_lanzamiento, pegi, id_desarrolladora) VALUES (?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getTitulo());
            ps.setInt(2, v.getAñoLanzamiento());
            ps.setInt(3, v.getPegi());
            ps.setInt(4, v.getIdDesarrolladora());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                return keys.getInt(1);
            }else{
                return 0;
            }
        } catch (SQLException e) {
            logger.error("Error al insertar el videojuego", e);
            throw new AccesoDatosException("Error al insertar el videojuego", e);
        }
    }

    public void actualizar(VideojuegoDTO v) {
        String sql = "UPDATE videojuegos SET titulo = ?, anio_lanzamiento = ?, pegi = ?, id_desarrolladora = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getTitulo());
            ps.setInt(2, v.getAñoLanzamiento());
            ps.setInt(3, v.getPegi());
            ps.setInt(4, v.getIdDesarrolladora());
            ps.setInt(5, v.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al actualizar el videojuego {}", v.getId(), e);
            throw new AccesoDatosException("Error al actualizar el videojuego", e);
        }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM videojuegos WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al borrar el videojuego {}", id, e);
            throw new AccesoDatosException("Error al borrar el videojuego", e);
        }
    }

    public int contarCopias(int idVideojuego) {
        String sql = "SELECT COUNT(*) FROM copias WHERE id_videojuego = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVideojuego);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.error("Error al contar las copias del videojuego {}", idVideojuego, e);
            throw new AccesoDatosException("Error al contar las copias", e);
        }
    }

    //MAS PRESTADOS
    public ArrayList<VideojuegoDTO> masPrestados() {
        String sql = "SELECT v.id, v.titulo, COUNT(*) AS total_prestamos "
                + "FROM prestamos p "
                + "JOIN copias c ON c.id = p.id_copia "
                + "JOIN videojuegos v ON v.id = c.id_videojuego "
                + "GROUP BY v.id, v.titulo "
                + "ORDER BY total_prestamos DESC, v.titulo "
                + "LIMIT 5";
        ArrayList<VideojuegoDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new VideojuegoDTO(
                            rs.getInt("id"), rs.getString("titulo"), rs.getInt("total_prestamos")));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener los videojuegos más prestados", e);
            throw new AccesoDatosException("Error al obtener el informe de más prestados", e);
        }
        return lista;
    }

    private VideojuegoDTO mapear(ResultSet rs) throws SQLException {
        return new VideojuegoDTO(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getInt("anio_lanzamiento"),
                rs.getInt("pegi"),
                rs.getInt("id_desarrolladora"),
                rs.getString("desarrolladora"),
                rs.getInt("copias_totales"),
                rs.getInt("copias_disponibles"));
    }

    //metodo auxiliar para setar los param
    public void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof LocalDate fecha) {
                ps.setDate(i + 1, Date.valueOf(fecha));
            } else {
                ps.setObject(i + 1, p);
            }
        }
    }

}
