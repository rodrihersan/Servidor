package com.dawes.ejerciciothymeleafampliacion.controlador;

import com.dawes.ejerciciothymeleafampliacion.modelo.Carrito;
import com.dawes.ejerciciothymeleafampliacion.modelo.LineaCarrito;
import com.dawes.ejerciciothymeleafampliacion.modelo.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ControladorPrincipal {
    @RequestMapping("/carrito")
    public String carrito(Model model){
        Producto producto1 = new Producto("Producto1", 5);
        Producto producto2 = new Producto("Producto2", 5);
        Producto producto3 = new Producto("Producto3", 5);

        Random r = new Random();
        int cantidad1 = r.nextInt(4)+1;
        int cantidad2 = r.nextInt(4)+1;
        int cantidad3 = r.nextInt(4)+1;

        LineaCarrito lineaCarrito = new LineaCarrito(producto1, cantidad1);
        LineaCarrito lineaCarrito2 = new LineaCarrito(producto2, cantidad2);
        LineaCarrito lineaCarrito3 = new LineaCarrito(producto3, cantidad3);

        Carrito carrito = new Carrito(lineaCarrito, lineaCarrito2,lineaCarrito3);

        model.addAttribute("carrito", carrito);
        return "carrito";
    }


}
