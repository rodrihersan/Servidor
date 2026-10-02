package com.dawes.ejerciciothymeleafampliacion.controlador;

import com.dawes.ejerciciothymeleafampliacion.modelo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
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

    @RequestMapping("/pasaporte")
    public String pasaporte(Model model){
        LocalDate fechaNacimiento = LocalDate.of(2002, 5, 15);


        Direccion direccion = new Direccion(
                "Calle Mayor",
                10,
                "Salamanca"
        );

        Persona persona = new Persona(
                "Rodrigo",
                "Hernandez",
                fechaNacimiento,
                direccion
        );

        LocalDate fechaActual = LocalDate.now();
        int edad = fechaActual.getYear() - persona.getFechaNacimiento().getYear();

        Random r = new Random();
        int numAleatorio = r.nextInt(9000)+1000;

        String codigoPasaporte = persona.getApellidos().substring(0, 2).toUpperCase() + "-" + persona.getFechaNacimiento().getYear() + "-" + numAleatorio;

        model.addAttribute("persona", persona);
        model.addAttribute("edad", edad);
        model.addAttribute("pasaporte", codigoPasaporte);

        return "pasaporte";
    }
}
