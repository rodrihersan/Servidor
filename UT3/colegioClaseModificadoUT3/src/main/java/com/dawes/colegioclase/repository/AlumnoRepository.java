package com.dawes.colegioclase.repository;

import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.dto.DesplegableDTO;
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
public class AlumnoRepository {
    private static final Logger logger = LoggerFactory.getLogger(AlumnoRepository.class);
    private final DataSource dataSource;

    public AlumnoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, Boolean familiaNumerosa, Boolean activo) {
        String sql = "SELECT a.id, a.nombre, a.apellidos, a.id_municipio, m.nombre AS municipio, a.familia_numerosa, a.activo " +
                "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio " +
                "WHERE a.nombre LIKE ? AND a.apellidos LIKE ? AND a.familia_numerosa = ? AND a.activo = ?";

        ArrayList<AlumnoDTO> listaAlumnos = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + apellido + "%");
            ps.setBoolean(3, familiaNumerosa);
            ps.setBoolean(4, activo);

            logger.info("Query a ejecutar: {}", ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaAlumnos.add(mapearAlumno(rs));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al buscar alumnos", e);
            cerrarConexion(connection);
        }
        return listaAlumnos;
    }

    public ArrayList<AlumnoDTO> filtrarAlumnos(String nombre, String apellido, Integer idMunicipio,
                                                      Boolean familiaNumerosa, Boolean activo) {
        StringBuilder sql = new StringBuilder(
                "SELECT a.id, a.nombre, a.apellidos, a.id_municipio, m.nombre AS municipio, "
                        + "a.familia_numerosa, a.activo "
                        + "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio "
                        + "WHERE 1 = 1");
        ArrayList<Object> parametros = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND a.nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (apellido != null && !apellido.isBlank()) {
            sql.append(" AND a.apellidos LIKE ?");
            parametros.add("%" + apellido + "%");
        }
        if (idMunicipio != null) {
            sql.append(" AND a.id_municipio = ?");
            parametros.add(idMunicipio);
        }
        if (familiaNumerosa != null) {
            sql.append(" AND a.familia_numerosa = ?");
            parametros.add(familiaNumerosa);
        }
        if (activo != null) {
            sql.append(" AND a.activo = ?");
            parametros.add(activo);
        }
        sql.append(" ORDER BY a.apellidos, a.nombre");
        ArrayList<AlumnoDTO> listaAlumnos = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps =
                    connection.prepareStatement(sql.toString());
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }
            logger.info("Query a ejecutar: {}", ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaAlumnos.add(mapearAlumno(rs));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar alumnos", e);
            cerrarConexion(connection);
        }
        return listaAlumnos;

    }

    public int insertar(AlumnoDTO alumno) {
        String sql = "INSERT INTO alumnos (id, nombre, apellidos, id_municipio, familia_numerosa, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, alumno.getId());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido());
            ps.setInt(4, alumno.getIdMunicipio());
            ps.setBoolean(5, alumno.getFamiliaNumerosa());
            ps.setBoolean(6, alumno.getActivo());


            filasAfectadas = ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al insertar el alumno {}", alumno.getId(), e);
            cerrarConexion(connection);
        }
        return filasAfectadas;
    }

    public AlumnoDTO buscarPorId(String id) {
        String sql = "SELECT a.id, a.nombre, a.apellidos, a.id_municipio, m.nombre AS municipio, "
                + "a.familia_numerosa, a.activo "
                + "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio "
                + "WHERE a.id = ?";
        AlumnoDTO alumno = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            logger.debug("Query a ejecutar: {}", ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = mapearAlumno(rs);
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al buscar el alumno {}", id, e);
            cerrarConexion(connection);
        }
        return alumno;
    }

    private AlumnoDTO mapearAlumno(ResultSet rs) throws SQLException {
        return new AlumnoDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getInt("id_municipio"),
                rs.getString("municipio"),
                rs.getBoolean("familia_numerosa"),
                rs.getBoolean("activo")
        );
    }

    //ACTUALIZAR
    public int actualizar(AlumnoDTO alumno) {
        String sql = "UPDATE alumnos SET nombre = ?, apellidos = ?, id_municipio = ?, "
                + "familia_numerosa = ?, activo = ? WHERE id = ?";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getIdMunicipio());
            ps.setBoolean(4, alumno.getFamiliaNumerosa());
            ps.setBoolean(5, alumno.getActivo());
            ps.setInt(6, alumno.getId());
            filasAfectadas = ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al actualizar el alumno {}",
                    alumno.getId(), e);
            cerrarConexion(connection);
        }
        return filasAfectadas;
    }

    public int eliminar(Integer id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            filasAfectadas = ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al eliminar el alumno {}", id, e);
            cerrarConexion(connection);
        }
        return filasAfectadas;
    }

    public AlumnoDTO obtenerAlumnoPorId(Integer id) {
        String sql = "SELECT a.id, a.nombre, a.apellidos, a.id_municipio, m.nombre AS municipio, "
                + "a.familia_numerosa, a.activo "
                + "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio "
                + "WHERE id = ?";
        AlumnoDTO alumno = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = mapearAlumno(rs);
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al obtener alumno por {}", id, e);
            cerrarConexion(connection);
        }
        return alumno;
    }

    public boolean esFamiliaNumerosa(Integer idAlumno) {
        String sql = "SELECT familia_numerosa FROM alumnos WHERE id = ?";
        boolean familiaNumerosa = false;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                familiaNumerosa = rs.getBoolean("familia_numerosa");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al comprobar si el alumno {} es familia numerosa", idAlumno, e);
            cerrarConexion(connection);
        }
        return familiaNumerosa;
    }

    public ArrayList<DesplegableDTO> listarAlumnosParaDesplegable() {
        String sql = "SELECT id, CONCAT(nombre, ' ', apellidos) AS descripcion "
                + "FROM alumnos WHERE activo = 1 ORDER BY apellidos";
        ArrayList<DesplegableDTO> listaAlumnos = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaAlumnos.add(new DesplegableDTO(rs.getInt("id"),
                        rs.getString("descripcion")));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al recuperar el desplegable de alumnos", e);
                    cerrarConexion(connection);
        }
        return listaAlumnos;
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
