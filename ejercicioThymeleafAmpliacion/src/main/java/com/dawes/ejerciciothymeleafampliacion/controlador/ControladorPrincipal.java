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
        model.addAttribute("edad");
        model.addAttribute("pasaporte", codigoPasaporte);

        return "pasaporte";
    }

    @RequestMapping("/resumen")
    public String resumen(Model model) {
        Alumno alumno = new Alumno("Rodrigo Hernández", LocalDate.of(2002, 5, 8), "Salamanca");

        Random r = new Random();
        int nota = r.nextInt(11);

        Evaluacion evaluacion = new Evaluacion(alumno, nota);

        model.addAttribute("evaluacion", evaluacion);
        model.addAttribute("modulo", "DAWES");
        return "resumen";
    }

    @RequestMapping("/matricula")
    public String matricula(Model model) {
        Vehiculo vehiculo = new Vehiculo("Seat", "Ibiza", 2012);

        Random r = new Random();
        int numero = r.nextInt(9999) + 1;
        String numeros = "" + numero;
        if (numero < 10) {
            numeros = "000" + numero;
        } else if (numero < 100) {
            numeros = "00" + numero;
        } else if (numero < 1000) {
            numeros = "0" + numero;
        }

        String consonantes = "BCDFGHJKLMNPRSTVWXYZ";
        String letras = "" + consonantes.charAt(r.nextInt(20))
                + consonantes.charAt(r.nextInt(20))
                + consonantes.charAt(r.nextInt(20));

        String matricula = numeros + " " + letras;

        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("matricula", matricula);
        return "matricula";
    }

    @RequestMapping("/batalla")
    public String batalla(Model model) {
        Random r = new Random();

        Luchador luchador1 = new Luchador("Ana", r.nextInt(51) + 50, r.nextInt(11) + 5);
        Luchador luchador2 = new Luchador("Luis", r.nextInt(51) + 50, r.nextInt(11) + 5);

        String resumen = "";
        for (int ronda = 1; ronda <= 3; ronda++) {
            luchador1.setVida(luchador1.getVida() - luchador2.getAtaque());
            luchador2.setVida(luchador2.getVida() - luchador1.getAtaque());

            resumen = resumen + "Ronda " + ronda + ": "
                    + luchador1.getNombre() + " (" + luchador1.getVida() + ") - "
                    + luchador2.getNombre() + " (" + luchador2.getVida() + ")   ";
        }

        String ganador;
        int diferencia;
        if (luchador1.getVida() > luchador2.getVida()) {
            ganador = luchador1.getNombre();
            diferencia = luchador1.getVida() - luchador2.getVida();
        } else if (luchador2.getVida() > luchador1.getVida()) {
            ganador = luchador2.getNombre();
            diferencia = luchador2.getVida() - luchador1.getVida();
        } else {
            ganador = "Empate";
            diferencia = 0;
        }

        model.addAttribute("resumen", resumen);
        model.addAttribute("ganador", ganador);
        model.addAttribute("diferencia", diferencia);
        return "batalla";
    }
}
