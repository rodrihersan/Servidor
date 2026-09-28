package com.dawes.ejerciciothymeleafbasico.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ControladorPincipal {
    @RequestMapping("/hora")
    public String hora(Model model){
        LocalTime hora = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        model.addAttribute("horaActual", hora.format(dtf));
        return "hora";
    }
}
