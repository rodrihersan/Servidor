import modelo.ProductoDAO;
import modelo.ProductoDTO;
import utils.ConexionBBDD;
import vista.ProductoVista;

import java.sql.*;
import java.util.ArrayList;

public class Principal {
    static void main(String[] args) {
        ProductoVista vp = new ProductoVista();
        vp.mostrarTodosProductos();
    }
}