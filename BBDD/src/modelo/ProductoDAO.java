package modelo;
import utils.ConexionBBDD;
import java.sql.*;
import java.util.ArrayList;


public class ProductoDAO {
    public ArrayList<ProductoDTO> obtenerTodosLosProductos() {
        ArrayList<ProductoDTO> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = ConexionBBDD.getConexion();
// 2. Preparamos la consulta
            String sql = "SELECT id, nombre, precio, stock, id_categoria FROM producto";
            ps = conexion.prepareStatement(sql);
// 3. Ejecutamos y obtenemos el ResultSet
            rs = ps.executeQuery();
// 4. Recorremos el ResultSet fila a fila
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                int idCategoria = rs.getInt("id_categoria");
                ProductoDTO producto = new ProductoDTO(id, nombre, precio,
                        stock, idCategoria);
                lista.add(producto);
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

