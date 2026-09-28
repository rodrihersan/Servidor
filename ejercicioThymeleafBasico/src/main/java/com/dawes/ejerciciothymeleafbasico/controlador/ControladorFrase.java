package com.dawes.ejerciciothymeleafbasico.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ControladorFrase {
    @RequestMapping("/frase")//esta es la ruta cuando iniciemos el localhost
    public String frase(Model frase){
        String[] frases = {"Hola que tal", "Al escondite inglés", "Ñam que rico", "Hoy hace buen día", "Nunca pares de aprender", "Java es divertido"};
        Random random = new Random();
        int posicion = random.nextInt(frases.length);
        frase.addAttribute("frase", frases[posicion]);
                /*esta palabra (de arriba) debe coincidir con el th en el .html*/
        return "FraseAleatoria";//esto debe coincidir ocn el html
    }
}
