package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
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
public class SociosRepository {
    private static final Logger logger = LoggerFactory.getLogger(SociosRepository.class);

    private final DataSource dataSource;

    public SociosRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<SocioDTO> buscar(SocioDTO filtro) {
        String sql_base = "SELECT s.id, s.nombre, s.apellidos, s.email, s.fecha_nacimiento, s.fecha_alta, s.activo, "
                + "(SELECT COUNT(*) FROM prestamos p WHERE p.id_socio = s.id AND p.fecha_devolucion IS NULL) AS prestamos_abiertos "
                + "FROM socios s";
        StringBuilder sql = new StringBuilder(sql_base).append(" WHERE 1 = 1");
        ArrayList<Object> params = new ArrayList<>();

        if (filtro.getNombre() != null && !filtro.getNombre().isBlank()) {
            sql.append(" AND CONCAT(s.nombre, ' ', s.apellidos) LIKE ?");
            params.add("%" + filtro.getNombre().trim() + "%");
        }
        if (filtro.getEmail() != null && !filtro.getEmail().isBlank()) {
            sql.append(" AND s.email LIKE ?");
            params.add("%" + filtro.getEmail().trim() + "%");
        }
        if (filtro.isActivo() != null) {
            sql.append(" AND s.activo = ?");
            params.add(filtro.isActivo());
        }
        sql.append(" ORDER BY s.apellidos, s.nombre");

        ArrayList<SocioDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar socios", e);
            throw new AccesoDatosException("Error al buscar socios", e);
        }
        return lista;
    }

    public SocioDTO buscarPorId(int id) {
        String sql = "SELECT s.id, s.nombre, s.apellidos, s.email, s.fecha_nacimiento, s.fecha_alta, s.activo, "
                + "(SELECT COUNT(*) FROM prestamos p WHERE p.id_socio = s.id AND p.fecha_devolucion IS NULL) AS prestamos_abiertos "
                + "FROM socios s WHERE s.id = ?";
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
            logger.error("Error al buscar el socio {}", id, e);
            throw new AccesoDatosException("Error al buscar el socio", e);
        }
    }

    /** ¿Hay otro socio con ese email? Para un alta */
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM socios WHERE email = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar el email {}", email, e);
            throw new AccesoDatosException("Error al comprobar el email", e);
        }
    }

    public int insertar(SocioDTO s) {
        String sql = "INSERT INTO socios (nombre, apellidos, email, fecha_nacimiento, fecha_alta, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getApellidos());
            ps.setString(3, s.getEmail());
            ps.setDate(4, Date.valueOf(s.getFechaNacimiento()));
            ps.setDate(5, Date.valueOf(s.getFechaAlta()));
            ps.setBoolean(6, s.isActivo());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                return keys.getInt(1);
            }else{
                return 0;
            }
        } catch (SQLException e) {
            logger.error("Error al insertar el socio", e);
            throw new AccesoDatosException("Error al insertar el socio", e);
        }
    }

    public void actualizar(SocioDTO s) {
        String sql = "UPDATE socios SET nombre = ?, apellidos = ?, email = ?, fecha_nacimiento = ?, activo = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getApellidos());
            ps.setString(3, s.getEmail());
            ps.setDate(4, Date.valueOf(s.getFechaNacimiento()));
            ps.setBoolean(5, s.isActivo());
            ps.setInt(6, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al actualizar el socio {}", s.getId(), e);
            throw new AccesoDatosException("Error al actualizar el socio", e);
        }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM socios WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al borrar el socio {}", id, e);
            throw new AccesoDatosException("Error al borrar el socio", e);
        }
    }


    public int contarPrestamos(int idSocio) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_socio = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.error("Error al contar los préstamos del socio {}", idSocio, e);
            throw new AccesoDatosException("Error al contar los préstamos", e);
        }
    }


    //Metodo auxiliar para mapear
    private SocioDTO mapear(ResultSet rs) throws SQLException {
        LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
        int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();

        return new SocioDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                fechaNacimiento,
                rs.getDate("fecha_alta").toLocalDate(),
                rs.getBoolean("activo"),
                edad,
                rs.getInt("prestamos_abiertos"));
    }

    // Metodo auxiliar para setar los param
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
