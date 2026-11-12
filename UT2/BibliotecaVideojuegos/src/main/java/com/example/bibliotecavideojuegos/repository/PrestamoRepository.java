package com.example.bibliotecavideojuegos.repository;

import com.example.bibliotecavideojuegos.dto.InformeVideojuegoDTO;
import com.example.bibliotecavideojuegos.dto.PrestamoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoRepository {

    private static final Logger logger = LoggerFactory.getLogger(PrestamoRepository.class);
    private final DataSource dataSource;

    public PrestamoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int contarPrestamosAbiertos(int idSocio) {

        String sql = "SELECT COUNT(*) AS total FROM prestamos " +
                "WHERE id_socio = ? AND fecha_devolucion IS NULL";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSocio);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                connection.close();
                return total;
            }

            connection.close();

        } catch (SQLException e) {
            logger.error("Error al contar los préstamos abiertos del socio", e);
            cerrarConexion(connection);
        }

        return 0;
    }

    public int insertarPrestamo(PrestamoDTO prestamo) {

        String sql = "INSERT INTO prestamos " +
                "(id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, prestamo.getIdSocio());
            ps.setInt(2, prestamo.getIdCopia());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaLimite()));
            ps.setNull(5, Types.DATE);
            ps.setDouble(6, prestamo.getMulta());

            int filasAfectadas = ps.executeUpdate();

            connection.close();
            return filasAfectadas;

        } catch (SQLException e) {
            logger.error("Error al insertar el préstamo", e);
            cerrarConexion(connection);
        }

        return 0;
    }

    public ArrayList<PrestamoDTO> listarPrestamos(String socio, String videojuego, String estado) {

        String sql = "SELECT p.id, p.id_socio, p.id_copia, " +
                "CONCAT(s.nombre, ' ', s.apellidos) AS socio, " +
                "v.titulo AS videojuego, c.codigo_inventario, " +
                "p.fecha_prestamo, p.fecha_limite, p.fecha_devolucion, p.multa " +
                "FROM prestamos p " +
                "INNER JOIN socios s ON p.id_socio = s.id " +
                "INNER JOIN copias c ON p.id_copia = c.id " +
                "INNER JOIN videojuegos v ON c.id_videojuego = v.id " +
                "WHERE 1 = 1";

        ArrayList<Object> parametros = new ArrayList<>();

        if (socio != null && !socio.isBlank()) {
            sql += " AND CONCAT(s.nombre, ' ', s.apellidos) LIKE ?";
            parametros.add("%" + socio + "%");
        }

        if (videojuego != null && !videojuego.isBlank()) {
            sql += " AND v.titulo LIKE ?";
            parametros.add("%" + videojuego + "%");
        }

        sql += " ORDER BY p.fecha_prestamo DESC";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();

            ArrayList<PrestamoDTO> lista = new ArrayList<>();

            while (rs.next()) {

                LocalDate fechaLimite = rs.getDate("fecha_limite").toLocalDate();

                LocalDate fechaDevolucion = null;

                if (rs.getDate("fecha_devolucion") != null) {
                    fechaDevolucion = rs.getDate("fecha_devolucion").toLocalDate();
                }

                String estadoPrestamo;
                int diasRetraso = 0;

                if (fechaDevolucion != null) {
                    estadoPrestamo = "DEVUELTO";
                } else if (fechaLimite.isBefore(LocalDate.now())) {
                    estadoPrestamo = "VENCIDO";
                    diasRetraso = (int) java.time.temporal.ChronoUnit.DAYS.between(
                            fechaLimite, LocalDate.now());
                } else {
                    estadoPrestamo = "EN PLAZO";
                }

                if (estado == null || estado.isBlank() || estado.equals(estadoPrestamo)) {
                    lista.add(new PrestamoDTO(
                            rs.getInt("id"),
                            rs.getInt("id_socio"),
                            rs.getInt("id_copia"),
                            rs.getString("socio"),
                            rs.getString("videojuego"),
                            rs.getString("codigo_inventario"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            fechaLimite,
                            fechaDevolucion,
                            rs.getDouble("multa"),
                            estadoPrestamo,
                            diasRetraso
                    ));
                }
            }

            connection.close();

            return lista;

        } catch (SQLException e) {
            logger.error("Error al listar préstamos: " + e.getMessage());
            cerrarConexion(connection);
            return new ArrayList<>();
        }
    }

    public boolean tienePrestamosVencidos(int idSocio) {

        String sql = "SELECT id FROM prestamos " + "WHERE id_socio = ? " + "AND fecha_devolucion IS NULL " + "AND fecha_limite < ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idSocio);
            ps.setDate(2, Date.valueOf(LocalDate.now()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                connection.close();
                return true;
            }

            connection.close();

        } catch (SQLException e) {
            logger.error("Error al comprobar los préstamos vencidos del socio", e);
            cerrarConexion(connection);
        }

        return false;
    }

    public PrestamoDTO obtenerPrestamoPorId(int idPrestamo) {

        String sql = "SELECT id, id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa " + "FROM prestamos WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    LocalDate fechaDevolucion = null;

                    if (rs.getDate("fecha_devolucion") != null) {
                        fechaDevolucion = rs.getDate("fecha_devolucion").toLocalDate();
                    }

                    return new PrestamoDTO(
                            rs.getInt("id"),
                            rs.getInt("id_socio"),
                            rs.getInt("id_copia"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_limite").toLocalDate(),
                            fechaDevolucion,
                            rs.getDouble("multa")
                    );
                }
            }

        } catch (SQLException e) {
            logger.error("Error al obtener el préstamo", e);
        }

        return null;
    }

    public int devolverPrestamo(int idPrestamo, LocalDate fechaDevolucion, double multa, boolean danada) {

        String sqlDevolver = "UPDATE prestamos SET fecha_devolucion = ?, multa = ? " + "WHERE id = ? AND fecha_devolucion IS NULL";

        String sqlCopia = "UPDATE copias SET estado = 'DAÑADO', activa = 0 " + "WHERE id = (SELECT id_copia FROM prestamos WHERE id = ?)";

        try (Connection connection = dataSource.getConnection()) {

            try {
                connection.setAutoCommit(false);

                int filasPrestamo;

                try (PreparedStatement ps = connection.prepareStatement(sqlDevolver)) {
                    ps.setDate(1, Date.valueOf(fechaDevolucion));
                    ps.setDouble(2, multa);
                    ps.setInt(3, idPrestamo);
                    filasPrestamo = ps.executeUpdate();
                }

                if (filasPrestamo != 1) {
                    connection.rollback();
                    return 0;
                }

                if (danada) {

                    try (PreparedStatement ps = connection.prepareStatement(sqlCopia)) {
                        ps.setInt(1, idPrestamo);

                        if (ps.executeUpdate() != 1) {
                            connection.rollback();
                            return 0;
                        }
                    }
                }

                connection.commit();
                return filasPrestamo;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            logger.error("Error al devolver el préstamo", e);
            return 0;
        }
    }

    // RENOVAR PRÉSTAMO

    public int renovarPrestamo(int idPrestamo, LocalDate nuevaFechaLimite) {

        String sql = "UPDATE prestamos SET fecha_limite = ? " + "WHERE id = ? AND fecha_devolucion IS NULL";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(nuevaFechaLimite));
            ps.setInt(2, idPrestamo);

            int filasAfectadas = ps.executeUpdate();

            connection.close();

            return filasAfectadas;

        } catch (SQLException e) {
            logger.error("Error al renovar el préstamo", e);
            cerrarConexion(connection);
        }

        return 0;
    }

    // INFORME DE LOS VIDEOJUEGOS MÁS PRESTADOS

    public ArrayList<InformeVideojuegoDTO> listarVideojuegosMasPrestados() {

        String sql = "SELECT v.id AS id_videojuego, v.titulo, COUNT(p.id) AS numero_prestamos " + "FROM prestamos p " + "INNER JOIN copias c ON p.id_copia = c.id " +
                "INNER JOIN videojuegos v ON c.id_videojuego = v.id " +
                "GROUP BY v.id, v.titulo " +
                "ORDER BY numero_prestamos DESC, v.titulo ASC" +
                "LIMIT 5";

        ArrayList<InformeVideojuegoDTO> lista = new ArrayList<>();

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new InformeVideojuegoDTO(
                        rs.getInt("id_videojuego"),
                        rs.getString("titulo"),
                        rs.getInt("numero_prestamos")
                ));
            }

            connection.close();

        } catch (SQLException e) {
            logger.error("Error al consultar los videojuegos más prestados", e);
            cerrarConexion(connection);
        }

        return lista;
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