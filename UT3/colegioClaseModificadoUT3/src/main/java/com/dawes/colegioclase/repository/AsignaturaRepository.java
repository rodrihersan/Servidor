package com.dawes.colegioclase.repository;

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
public class AsignaturaRepository {
    private static final Logger logger = LoggerFactory.getLogger(AlumnoRepository.class);
    private final DataSource dataSource;

    public AsignaturaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public double obtenerTasaAsignatura(Integer idAsignatura) {
        String sql = "SELECT tasa FROM asignaturas WHERE id = ?";
        double tasa = 0.0;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idAsignatura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tasa = rs.getDouble("tasa");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al obtener la tasa de la asignatura {}", idAsignatura, e);
            cerrarConexion(connection);
        }
        return tasa;
    }

    public ArrayList<DesplegableDTO> listarAsignaturasParaDesplegable() {
        String sql = "SELECT id, nombre FROM asignaturas ORDER BY nombre";
        ArrayList<DesplegableDTO> listaAsignaturas = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaAsignaturas.add(new
                        DesplegableDTO(rs.getInt("id"), rs.getString("nombre")));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al recuperar el desplegable de asignaturas", e);
                    cerrarConexion(connection);
        }
        return listaAsignaturas;
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
