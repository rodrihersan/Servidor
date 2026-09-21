package utils;
import java.sql.*;

public class ConexionBBDD {
    public static Connection getConexion() {
        String servidor = "jdbc:mysql://localhost:3306/tienda";
        String usuario = "root";
        String contraseña = "PracticaRoot";
        try {
            Connection conexion = DriverManager.getConnection(servidor, usuario, contraseña);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
