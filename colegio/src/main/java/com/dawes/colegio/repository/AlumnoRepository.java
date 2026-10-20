package com.dawes.colegio.repository;

import com.dawes.colegio.dto.AlumnoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    //LISTAR
    public ArrayList<AlumnoDTO> buscarAlumnos(String nombre, String apellido, boolean familiaNumerosa, boolean activo) {
        String sql = "SELECT a.id, a.nombre, a.apellidos, a.id_municipio, m.nombre AS municipio, "
                + "a.familia_numerosa, a.activo "
                + "FROM alumnos a JOIN municipios m ON a.id_municipio = m.id_municipio "
                + "WHERE a.nombre LIKE ? AND a.apellidos LIKE ? "
                + "AND a.familia_numerosa = ? AND a.activo = ?";
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

    private AlumnoDTO mapearAlumno(ResultSet rs) throws SQLException {
        return new AlumnoDTO(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getInt("id_municipio"),
                rs.getString("municipio"),
                rs.getBoolean("familia_numerosa"),
                rs.getBoolean("activo")
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
