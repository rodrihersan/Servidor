package com.dawes.hernandezsanchezrodrigout1.controlador;

import com.dawes.hernandezsanchezrodrigout1.modelo.Equipo;
import com.dawes.hernandezsanchezrodrigout1.modelo.Jugador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ControladorPrincipal {
    @RequestMapping("/jugador")
    public String jugador(Model model){
        Jugador jugador = new Jugador("Llul", "Real Madrid", 33, 5, 8, 5);

        model.addAttribute("jugador", jugador);
        return "jugador";
    }

    @RequestMapping("/mvp")
    public String mvp(Model model){
        Random r = new Random();
        String[] nombreJugadores ={"Llul", "Marcos", "Lebron", "Curry", "Alberto"};
        String mvp = nombreJugadores[r.nextInt(5)];

        model.addAttribute("mvp", mvp);
        return "mvp";
    }

    @RequestMapping("/partido")
    public String partido(Model model){
        Random r = new Random();
        Equipo equipo1 = new Equipo("Real Madrid", "Madrid", r.nextInt(71) + 39);
        Equipo equipo2 = new Equipo("Angeles Lackers", "LA/EEUU", r.nextInt(71) + 39);

        String ganador;
        int diferencia;
        if (equipo1.getPuntos() > equipo2.getPuntos()) {
            ganador = equipo1.getNombre();
            diferencia = equipo1.getPuntos() - equipo2.getPuntos();
        } else if (equipo2.getPuntos() > equipo1.getPuntos()) {
            ganador = equipo2.getNombre();
            diferencia = equipo2.getPuntos() - equipo1.getPuntos();
        } else {
            ganador = "Empate";
            diferencia = 0;
        }
        model.addAttribute("equipo1", equipo1);
        model.addAttribute("equipo2", equipo2);
        model.addAttribute("ganador", ganador);
        model.addAttribute("diferencia", diferencia);
        return "partido";
    }
}
