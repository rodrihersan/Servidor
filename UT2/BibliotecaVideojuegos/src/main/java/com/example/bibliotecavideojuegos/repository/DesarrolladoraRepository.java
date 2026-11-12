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
public class DesarrolladoraRepository {
    private static final Logger logger = LoggerFactory.getLogger(DesarrolladoraRepository.class);
    private final DataSource dataSource;

    public DesarrolladoraRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<DesplegableDTO> listaDesplegable() {
        String sql = "SELECT id, nombre FROM desarrolladoras ORDER BY nombre";
        ArrayList<DesplegableDTO> listaDesarrolladoras = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaDesarrolladoras.add(new DesplegableDTO(rs.getInt("id"), rs.getString("nombre")));
            }
            connection.close();

        } catch (SQLException e) {
            logger.error("Error al recuperar el desplegable de desarrolladoras", e);
            cerrarConexion(connection);
        }
        return listaDesarrolladoras;
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