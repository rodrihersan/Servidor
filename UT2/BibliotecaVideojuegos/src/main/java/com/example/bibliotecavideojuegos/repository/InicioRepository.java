package com.example.bibliotecavideojuegos.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class InicioRepository {

    private static final Logger logger = LoggerFactory.getLogger(InicioRepository.class);
    private final DataSource dataSource;

    public InicioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int contarVideojuegos() {
        return contarFilas("SELECT COUNT(*) AS total FROM videojuegos");
    }

    public int contarSociosActivos() {
        return contarFilas("SELECT COUNT(*) AS total FROM socios WHERE activo = 1");
    }

    public int contarPrestamosAbiertos() {
        return contarFilas("SELECT COUNT(*) AS total FROM prestamos WHERE fecha_devolucion IS NULL");
    }

    public int contarPrestamosVencidos() {

        return contarFilas("SELECT COUNT(*) AS total FROM prestamos " + "WHERE fecha_devolucion IS NULL AND fecha_limite < CURDATE()");
    }

    private int contarFilas(String sql) {
        // try-with-resources: Java cierra la conexión y el statement solo al terminar
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // COUNT(*) siempre devuelve una fila con el total
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            logger.error("Error al contar filas para el inicio", e);
        }

        return 0;
    }
}
