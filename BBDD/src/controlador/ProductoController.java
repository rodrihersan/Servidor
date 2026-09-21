package controlador;

import modelo.ProductoDAO;
import modelo.ProductoDTO;

import java.util.ArrayList;

public class ProductoController {
    public ArrayList<ProductoDTO> obtenerTodosLosProductos(){
        ProductoDAO dao = new ProductoDAO();
        ArrayList<ProductoDTO> lista =
                dao.obtenerTodosLosProductos();
        return lista;
    }
}
