package com.dawes.colegioclase.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

@Repository
public class MatriculacionRepository {
    private static final Logger logger = LoggerFactory.getLogger(MatriculacionRepository.class);
    private final DataSource dataSource;

    public MatriculacionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public int contarAsignaturasMatriculadas(Integer idAlumno) {
        String sql = "SELECT COUNT(*) AS total FROM matriculaciones WHERE id_alumno = ? AND activo = 1";
        int total = 0;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps =
                    connection.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al contar las asignaturas matriculadas del alumno {}", idAlumno, e);
            cerrarConexion(connection);
        }
        return total;
    }

    public int insertarMatriculacion(Integer idAlumno, Integer idAsignatura,
                                     LocalDate fecha, double tasa) {
        String sqlMatriculacion = "INSERT INTO matriculaciones (id_alumno, id_asignatura, fecha, activo) "
                + "VALUES (?, ?, ?, 1)";
        String sqlCaja = "INSERT INTO caja (idmatricula, importe) VALUES (?, ?)";
        Connection connection = null;
        int filasAfectadas = 0;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false); // Empieza la transacción
            PreparedStatement psMatriculacion = connection.prepareStatement(sqlMatriculacion,
                    Statement.RETURN_GENERATED_KEYS);
            psMatriculacion.setInt(1, idAlumno);
            psMatriculacion.setInt(2, idAsignatura);
            psMatriculacion.setDate(3, Date.valueOf(fecha));
            int filasMatriculacion = psMatriculacion.executeUpdate();
            if (filasMatriculacion == 0) {
                throw new SQLException("No se ha podido insertar la matriculación");
            }

            // Recuperamos el id autogenerado de la matriculación para usarlo en el insert de caja
            ResultSet clavesGeneradas = psMatriculacion.getGeneratedKeys();
            int idMatriculacion;
            if (clavesGeneradas.next()) {
                idMatriculacion = clavesGeneradas.getInt(1);
            } else {
                throw new SQLException("No se ha podido recuperar el id de la matriculación");
            }

            PreparedStatement psCaja = connection.prepareStatement(sqlCaja);
            psCaja.setInt(1, idMatriculacion);
            psCaja.setDouble(2, tasa);
            int filasCaja = psCaja.executeUpdate();
            if (filasCaja == 0) {
                throw new SQLException("No se ha podido insertar el pago en caja");
            }
            connection.commit(); // Las dos inserciones han ido bien:confirmamos
            filasAfectadas = filasCaja;
        } catch (SQLException e) {
            logger.error("Error al insertar la matriculación del alumno {}", idAlumno, e);
            if (connection != null) {
                try {
                    connection.rollback(); // Deshacemos lo que se hubiera llegado a insertar
                } catch (SQLException ex) {
                    logger.error("Error al hacer rollback", ex);
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Restauramos el autocommit
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return filasAfectadas;
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


