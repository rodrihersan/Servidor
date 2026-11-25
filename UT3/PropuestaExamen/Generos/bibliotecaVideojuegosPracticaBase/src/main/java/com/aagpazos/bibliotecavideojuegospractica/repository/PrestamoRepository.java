package com.aagpazos.bibliotecavideojuegospractica.repository;

import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.ResumenDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.AccesoDatosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrestamoRepository {
    private static final Logger logger = LoggerFactory.getLogger(PrestamoRepository.class);
    private final DataSource dataSource;

    public PrestamoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<PrestamoDTO> buscar(PrestamoDTO filtro) {
        String sql_base =
                "SELECT p.id, p.id_socio, s.nombre,  s.apellidos, p.id_copia, c.codigo_inventario, "
                        + "v.titulo, pl.nombre AS plataforma, p.fecha_prestamo, p.fecha_limite, p.fecha_devolucion, p.multa "
                        + "FROM prestamos p "
                        + "JOIN socios s ON s.id = p.id_socio "
                        + "JOIN copias c ON c.id = p.id_copia "
                        + "JOIN videojuegos v ON v.id = c.id_videojuego "
                        + "JOIN plataformas pl ON pl.id = c.id_plataforma";
        StringBuilder sql = new StringBuilder(sql_base).append(" WHERE 1 = 1");
        ArrayList<Object> params = new ArrayList<>();

        if (filtro.getSocio() != null && !filtro.getSocio().isBlank()) {
            sql.append(" AND CONCAT(s.nombre, ' ', s.apellidos) LIKE ?");
            params.add("%" + filtro.getSocio().trim() + "%");
        }
        if (filtro.getTitulo() != null && !filtro.getTitulo().isBlank()) {
            sql.append(" AND v.titulo LIKE ?");
            params.add("%" + filtro.getTitulo().trim() + "%");
        }
        if (filtro.getEstado() != null) {
            switch (filtro.getEstado()) {
                case "EN_PLAZO" -> sql.append(" AND p.fecha_devolucion IS NULL AND p.fecha_limite >= CURDATE()");
                case "VENCIDO" -> sql.append(" AND p.fecha_devolucion IS NULL AND p.fecha_limite < CURDATE()");
                case "DEVUELTO" -> sql.append(" AND p.fecha_devolucion IS NOT NULL");
                case "ABIERTO" -> sql.append(" AND p.fecha_devolucion IS NULL"); // en plazo + vencidos (pantalla de devoluciones)
                default -> { /* el Service ya ha validado el valor */ }
            }
        }
        if (filtro.getFechaPrestamo() != null) {
            sql.append(" AND p.fecha_prestamo >= ?");
            params.add(filtro.getFechaPrestamo());
        }
        sql.append(" ORDER BY p.fecha_prestamo DESC, p.id DESC");

        ArrayList<PrestamoDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar préstamos", e);
            throw new AccesoDatosException("Error al buscar préstamos", e);
        }
        return lista;
    }

    private PrestamoDTO mapear(ResultSet rs) throws SQLException {
        //Calculamos estado
        LocalDate fechaLimite = rs.getDate("fecha_limite").toLocalDate();
        Date fechaDevolucionSql = rs.getDate("fecha_devolucion"); // Puede ser null
        LocalDate fechaDevolucion = (fechaDevolucionSql != null) ? fechaDevolucionSql.toLocalDate() : null;

        String estado;
        if (fechaDevolucion != null) {
            estado = "DEVUELTO";
        } else if (fechaLimite.isBefore(LocalDate.now())) {
            estado = "VENCIDO";
        } else {
            estado = "EN_PLAZO";
        }

        //Calculo dias de retrado
        // Determinar la fecha final de cálculo (fecha de devolución o fecha actual)
        LocalDate fechaFin =  LocalDate.now();
        if(fechaDevolucion != null) {
            fechaFin = fechaDevolucion;
        }
        long diasDiferencia = ChronoUnit.DAYS.between(fechaLimite, fechaFin);
        // Asegurar que no sea negativo (equivalente a GREATEST(..., 0))
        int diasRetraso = (int) Math.max(diasDiferencia, 0);

        return new PrestamoDTO(
                rs.getInt("id"),
                rs.getInt("id_socio"),
                rs.getString("nombre") + " " + rs.getString("apellidos"),
                rs.getInt("id_copia"),
                rs.getString("codigo_inventario"),
                rs.getString("titulo"),
                rs.getString("plataforma"),
                rs.getDate("fecha_prestamo").toLocalDate(),
                rs.getDate("fecha_limite").toLocalDate(),
                fechaDevolucion,
                rs.getDouble("multa"),
                estado,
                diasRetraso,
                0
        );
    }

    /** Devuelve el préstamo o null si no existe. */
    public PrestamoDTO buscarPorId(int id) {
        String sql =
                "SELECT p.id, p.id_socio, s.nombre,  s.apellidos, p.id_copia, c.codigo_inventario, "
                        + "v.titulo, pl.nombre AS plataforma, p.fecha_prestamo, p.fecha_limite, p.fecha_devolucion, p.multa "
                        + "FROM prestamos p "
                        + "JOIN socios s ON s.id = p.id_socio "
                        + "JOIN copias c ON c.id = p.id_copia "
                        + "JOIN videojuegos v ON v.id = c.id_videojuego "
                        + "JOIN plataformas pl ON pl.id = c.id_plataforma"
                        + " WHERE p.id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapear(rs);
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar el préstamo {}", id, e);
            throw new AccesoDatosException("Error al buscar el préstamo", e);
        }
    }

    public int insertar(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        String sql = "INSERT INTO prestamos (id_socio, id_copia, fecha_prestamo, fecha_limite, multa) VALUES (?, ?, ?, ?, 0)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idSocio);
            ps.setInt(2, idCopia);
            ps.setDate(3, Date.valueOf(fechaPrestamo));
            ps.setDate(4, Date.valueOf(fechaLimite));

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                return keys.getInt(1);
            }else{
                return 0;
            }

        } catch (SQLException e) {
            logger.error("Error al insertar el préstamo", e);
            throw new AccesoDatosException("Error al insertar el préstamo", e);
        }
    }


    /** Préstamos abiertos con la fecha límite ya pasada (regla RN-5). */
    public int contarVencidosSinDevolver(int idSocio, LocalDate hoy) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_socio = ? AND fecha_devolucion IS NULL AND fecha_limite < ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setDate(2, Date.valueOf(hoy));
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    return rs.getInt(1);
                else
                    return 0;
            }
        } catch (SQLException e) {
            logger.error("Error al contar préstamos vencidos del socio {}", idSocio, e);
            throw new AccesoDatosException("Error al contar préstamos vencidos", e);
        }
    }

    /**
     * Devolución en UNA transacción: se cierra el préstamo y, si la copia vuelve dañada, se da de baja.
     * Devuelve false si el préstamo ya estaba devuelto (no se ha modificado nada).
     */
    public boolean registrarDevolucion(int idPrestamo, int idCopia, LocalDate fecha, Double multa, boolean copiaDanada) {
        String sqlPrestamo = "UPDATE prestamos SET fecha_devolucion = ?, multa = ? WHERE id = ? AND fecha_devolucion IS NULL";
        String sqlCopia = "UPDATE copias SET estado = 'DANADO', activa = FALSE WHERE id = ?";
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false); // empieza la transacción
            try {
                int filas;
                try (PreparedStatement ps = con.prepareStatement(sqlPrestamo)) {
                    ps.setDate(1, Date.valueOf(fecha));
                    ps.setDouble(2, multa);
                    ps.setInt(3, idPrestamo);
                    filas = ps.executeUpdate();
                }
                if (filas == 0) {
                    con.rollback();
                    return false;
                }
                if (copiaDanada) {
                    try (PreparedStatement ps = con.prepareStatement(sqlCopia)) {
                        ps.setInt(1, idCopia);
                        ps.executeUpdate();
                    }
                }
                con.commit(); // las dos operaciones han ido bien
                return true;
            } catch (SQLException | RuntimeException e) {
                con.rollback(); // algo ha fallado: no se queda nada a medias
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Error al registrar la devolución del préstamo {}", idPrestamo, e);
            throw new AccesoDatosException("Error al registrar la devolución", e);
        }
    }



    public ResumenDTO resumen() {
        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM videojuegos) AS videojuegos, "
                + "(SELECT COUNT(*) FROM socios WHERE activo = TRUE) AS socios_activos, "
                + "(SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL) AS abiertos, "
                + "(SELECT COUNT(*) FROM prestamos WHERE fecha_devolucion IS NULL AND fecha_limite < CURDATE()) AS vencidos";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return new ResumenDTO(rs.getInt("videojuegos"), rs.getInt("socios_activos"),
                    rs.getInt("abiertos"), rs.getInt("vencidos"));
        } catch (SQLException e) {
            logger.error("Error al calcular el resumen", e);
            throw new AccesoDatosException("Error al calcular el resumen", e);
        }
    }

    // RENOVAL
    public boolean renovar(int idPrestamo, LocalDate nuevaFechaLimite) {
        String sql = "UPDATE prestamos "
                + "SET fecha_limite = ?, renovaciones = renovaciones + 1 "
                + "WHERE id = ? ";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nuevaFechaLimite);
            ps.setInt(2, idPrestamo);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al renovar el préstamo {}", idPrestamo, e);
            throw new AccesoDatosException("Error al renovar el préstamo", e);
        }
    }


    //metodo auxiliar para setar los param
    public void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object p = params.get(i);
            if (p instanceof LocalDate fecha) {
                ps.setDate(i + 1, Date.valueOf(fecha));
            } else {
                ps.setObject(i + 1, p);
            }
        }
    }
}
