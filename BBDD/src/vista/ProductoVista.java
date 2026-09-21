package vista;

import modelo.ProductoDAO;
import modelo.ProductoDTO;

import java.util.ArrayList;

public class ProductoVista {
    public void mostrarTodosProductos() {
        ProductoDAO dao = new ProductoDAO();
        ArrayList<ProductoDTO> lista = dao.obtenerTodosLosProductos();
// Ver datos
        for (ProductoDTO producto : lista) {
            System.out.println(producto.getId() + "-" +
                    producto.getNombre() + "-" + producto.getPrecio() + "-" +
                    producto.getStock() + "-" + producto.getIdCategoria());
        }
    }
}

