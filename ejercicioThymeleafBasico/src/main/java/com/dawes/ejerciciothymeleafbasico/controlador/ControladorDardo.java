package com.dawes.ejerciciothymeleafbasico.controlador;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ControladorDado {
    @RequestMapping("/dado")
    public String dado(Model dado){
        Random random = new Random();
        int posicion = random.nextInt(6) + 1;
        dado.addAttribute("dado", posicion);
    }
}
