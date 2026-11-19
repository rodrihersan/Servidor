package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
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
public class DesarrolladoraRepository {
    private static final Logger logger = LoggerFactory.getLogger(DesarrolladoraRepository.class);

    private final DataSource dataSource;

    public DesarrolladoraRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<DesarrolladoraDTO> listarDesarrolladoras() {
        String sql = "SELECT id, nombre, pais FROM desarrolladoras ORDER BY nombre";
        ArrayList<DesarrolladoraDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new DesarrolladoraDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("pais")));
            }
        } catch (SQLException e) {
            logger.error("Error al listar desarrolladoras", e);
            throw new AccesoDatosException("Error al listar desarrolladoras", e);
        }
        return lista;
    }

    public boolean existeDesarrolladora(int id) {
        String sql = "SELECT COUNT(*) FROM desarrolladoras WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar la existencia del id {}", id, e);
            throw new AccesoDatosException("Error al comprobar la existencia de un registro", e);
        }
    }



}
