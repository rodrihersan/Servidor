package modelo;
import utils.ConexionBBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoDAO {
    public ArrayList<AlumnoDTO> obtenerTodosLosAlumnos() {
        ArrayList<AlumnoDTO> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = ConexionBBDD.getConexion();
// 2. Preparamos la consulta
             String sql = "SELECT id, nombre, email, edad,  FROM alumno";
            ps = conexion.prepareStatement(sql);
// 3. Ejecutamos y obtenemos el ResultSet
            rs = ps.executeQuery();
// 4. Recorremos el ResultSet fila a fila
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int edad = rs.getInt("edad");
                AlumnoDTO alumno = new AlumnoDTO(id, nombre, email, edad);
                lista.add(alumno);
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error en la BBDD: " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " +
                        e.getMessage());
            }
        }
        return lista;
    }
}