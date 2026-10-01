package com.dawes.ejerciciothymeleafbasico.controlador;

import com.dawes.ejerciciothymeleafbasico.modelo.Estudiante;
import com.dawes.ejerciciothymeleafbasico.modelo.Personaje;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Controller
public class ControladorPincipal {
    @RequestMapping("/hora")
    public String hora(Model model){
        LocalTime hora = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        model.addAttribute("horaActual", hora.format(dtf));
        return "hora";
    }
    //ejercicio 2 y 3 realizados en otro controlador. Siguientes ejercicios
    //aqui

    //ejercicio4
    @RequestMapping("/password")
    public String generarPassword(Model model) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = "";
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int posicion = random.nextInt(caracteres.length());
            password = password + caracteres.charAt(posicion);
        }

        model.addAttribute("password", password);
        return "password";
    }

    //ejercicio 5
    @RequestMapping ("/personaje")
    public String personaje(Model model){
        Personaje personaje = new Personaje("Prueba", 5, "dfs", "fkaj");
        model.addAttribute("persona", personaje);
        return "personajeVista";
    }

    //ejercicio 6
    @RequestMapping("/saludoHora")
    public String saludoHora(Model model){
        Personaje personaje = new Personaje("Prueba", 5, "dfs", "fkaj");

        LocalTime hora = LocalTime.now();
        String nombre = " Rodrigo";
        String saludo = "";
        if(hora.getHour() >=6 && hora.getHour() <=12){
            saludo = "Buenos dias";
        }else if (hora.getHour() >12 && hora.getHour() <=21){
            saludo = "Buenas tardes";
        }else{
            saludo = "Buenas noches";
        }
        String saludoFinal = saludo + nombre;
        model.addAttribute("saludoFinal", saludoFinal);

        return "saludoTemporal";
    }

    @RequestMapping("/estudiante")
    public String estudiante(Model model){
        Estudiante estudiante = new Estudiante("Rodrigo", "Hernandez", "DIW");
        Random random = new Random();
        int nota = random.nextInt(10);

        String respuesta = estudiante.getNombre() + estudiante.getApellido() + "ha sacado un " + nota + " en la asignatura " + estudiante.getAsignatura();
        model.addAttribute("respuesta", respuesta);

        return "estudiante";
    }
}
