package com.dawes.colegio.repository;

import com.dawes.colegio.dto.DesplegableDTO;
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
public class MunicipioRepository {
    private static final Logger logger = LoggerFactory.getLogger(MunicipioRepository.class);
    private final DataSource dataSource;
    public MunicipioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public ArrayList<DesplegableDTO> listarParaDesplegable() {
        String sql = "SELECT id_municipio, nombre FROM municipios ORDER BY nombre";
        ArrayList<DesplegableDTO> listaMunicipios = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaMunicipios.add(new DesplegableDTO(rs.getInt("id_municipio"), rs.getString("nombre")));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al recuperar el desplegable de municipios", e);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return listaMunicipios;
    }
}
