package com.dawes.practicaexamen.controlador;

import com.dawes.practicaexamen.modelo.Equipo;
import com.dawes.practicaexamen.modelo.Jugador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ControladorPrincipal {

    // 1. Jugador
    @RequestMapping("/jugador")
    public String jugador(Model model) {
        Jugador jugador = new Jugador("Faker", "T1", 8, 2, 10);

        model.addAttribute("jugador", jugador);
        return "jugador";
    }

    // 2. MVP aleatorio
    @RequestMapping("/mvp")
    public String mvp(Model model) {
        Random r = new Random();
        String[] nombresJugadores = {"Faker", "Chovy", "Zeus", "Keria", "Oner"};
        String mvp = nombresJugadores[r.nextInt(5)];

        model.addAttribute("mvp", mvp);
        return "mvp";
    }

    // 3. Enfrentamiento
    @RequestMapping("/enfrentamiento")
    public String enfrentamiento(Model model) {
        Random r = new Random();

        Equipo equipo1 = new Equipo("T1", "LCK", r.nextInt(40) + 60);
        Equipo equipo2 = new Equipo("Gen.G", "LCK", r.nextInt(40) + 60);

        String resultado;
        if (equipo1.getFuerza() > equipo2.getFuerza()) {
            int diferencia = equipo1.getFuerza() - equipo2.getFuerza();
            resultado = "Gana " + equipo1.getNombre() + " por " + diferencia + " puntos";
        } else if (equipo2.getFuerza() > equipo1.getFuerza()) {
            int diferencia = equipo2.getFuerza() - equipo1.getFuerza();
            resultado = "Gana " + equipo2.getNombre() + " por " + diferencia + " puntos";
        } else {
            resultado = "Empate";
        }

        model.addAttribute("equipo1", equipo1);
        model.addAttribute("equipo2", equipo2);
        model.addAttribute("resultado", resultado);
        return "enfrentamiento";
    }






   /* @RequestMapping("/enfrentamiento")
    public String enfrentamiento(Model model) {
        Equipo equipo1 = new Equipo("T1", "LCK");
        Equipo equipo2 = new Equipo("Gen.G", "LCK");

        String resultado;
        if (equipo1.getFuerza() > equipo2.getFuerza()) {
            int diferencia = equipo1.getFuerza() - equipo2.getFuerza();
            resultado = "Gana " + equipo1.getNombre() + " por " + diferencia + " puntos";
        } else if (equipo2.getFuerza() > equipo1.getFuerza()) {
            int diferencia = equipo2.getFuerza() - equipo1.getFuerza();
            resultado = "Gana " + equipo2.getNombre() + " por " + diferencia + " puntos";
        } else {
            resultado = "Empate";
        }

        model.addAttribute("equipo1", equipo1);
        model.addAttribute("equipo2", equipo2);
        model.addAttribute("resultado", resultado);
        return "enfrentamiento";*/
    }