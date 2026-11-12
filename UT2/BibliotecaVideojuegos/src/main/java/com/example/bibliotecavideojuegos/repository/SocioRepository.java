
package com.example.bibliotecavideojuegos.repository;

import com.example.bibliotecavideojuegos.dto.DesplegableDTO;
import com.example.bibliotecavideojuegos.dto.SocioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class SocioRepository {

    private static final Logger logger = LoggerFactory.getLogger(SocioRepository.class);
    private final DataSource dataSource;

    public SocioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<SocioDTO> filtrarSocios(String nombre, String email, Boolean activo) {

        StringBuilder sql = new StringBuilder("SELECT s.id, s.nombre, s.apellidos, s.email, " +
                "s.fecha_nacimiento, s.activo, s.fecha_alta, " +
                "(SELECT COUNT(*) FROM prestamos p " +
                "WHERE p.id_socio = s.id AND p.fecha_devolucion IS NULL) AS prestamos_abiertos " +
                "FROM socios s WHERE 1 = 1"
        );

        ArrayList<Object> parametros = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND (s.nombre LIKE ? OR s.apellidos LIKE ?)");
            parametros.add("%" + nombre + "%");
            parametros.add("%" + nombre + "%");
        }

        if (email != null && !email.isBlank()) {
            sql.append(" AND s.email LIKE ?");
            parametros.add("%" + email + "%");
        }

        if (activo != null) {
            sql.append(" AND s.activo = ?");
            parametros.add(activo);
        }

        sql.append(" ORDER BY s.apellidos, s.nombre");

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql.toString());

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();

            ArrayList<SocioDTO> listaSocios = new ArrayList<>();

            while (rs.next()) {
                listaSocios.add(mapearSocio(rs));
            }

            connection.close();

            return listaSocios;

        } catch (SQLException e) {
            logger.error("Error al filtrar socios: " + e.getMessage());
            cerrarConexion(connection);
            return new ArrayList<>();
        }
    }

    public ArrayList<DesplegableDTO> listaSociosActivos() {

        String sql = "SELECT id, CONCAT(nombre, ' ', apellidos) AS descripcion " +
                "FROM socios WHERE activo = TRUE ORDER BY apellidos, nombre";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            ArrayList<DesplegableDTO> listaSocios = new ArrayList<>();

            while (rs.next()) {
                listaSocios.add(new DesplegableDTO(
                        rs.getInt("id"),
                        rs.getString("descripcion")
                ));
            }

            connection.close();

            return listaSocios;

        } catch (SQLException e) {
            logger.error("Error al obtener los socios activos: " + e.getMessage());
            cerrarConexion(connection);
            return new ArrayList<>();
        }
    }

    public boolean esSocioActivo(int idSocio) {

        String sql = "SELECT activo FROM socios WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSocio);

            ResultSet rs = ps.executeQuery();

            boolean activo = false;

            if (rs.next()) {
                activo = rs.getBoolean("activo");
            }

            connection.close();

            return activo;

        } catch (SQLException e) {
            logger.error("Error al comprobar si el socio está activo: " + e.getMessage());
            cerrarConexion(connection);
            return false;
        }
    }

    public LocalDate obtenerFechaNacimiento(int idSocio) {

        String sql = "SELECT fecha_nacimiento FROM socios WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSocio);

            ResultSet rs = ps.executeQuery();

            LocalDate fechaNacimiento = null;

            if (rs.next()) {
                fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
            }

            connection.close();

            return fechaNacimiento;

        } catch (SQLException e) {
            logger.error("Error al obtener la fecha de nacimiento del socio: " + e.getMessage());
            cerrarConexion(connection);
            return null;
        }
    }

    public boolean existeEmail(String email) {

        String sql = "SELECT id FROM socios WHERE email = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            boolean existe = rs.next();

            connection.close();

            return existe;

        } catch (SQLException e) {
            logger.error("Error al comprobar el email del socio: " + e.getMessage());
            cerrarConexion(connection);
            return false;
        }
    }

    public int insertarSocio(SocioDTO socio) {

        String sql = "INSERT INTO socios (nombre, apellidos, email, fecha_nacimiento, fecha_alta, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setString(3, socio.getEmail());
            ps.setDate(4, java.sql.Date.valueOf(socio.getFechaNacimiento()));
            ps.setDate(5, java.sql.Date.valueOf(socio.getFechaAlta()));
            ps.setBoolean(6, socio.getActivo());

            int filasAfectadas = ps.executeUpdate();

            connection.close();

            return filasAfectadas;

        } catch (SQLException e) {
            logger.error("Error al insertar el socio: " + e.getMessage());
            cerrarConexion(connection);
            return 0;
        }
    }

    private SocioDTO mapearSocio(ResultSet rs) throws SQLException {
        return new SocioDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getBoolean("activo"),
                rs.getInt("prestamos_abiertos"),
                rs.getDate("fecha_alta").toLocalDate()
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
