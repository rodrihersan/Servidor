package com.dawes.hernandezsanchezrodrigopracticaexamen1.controlador;

import com.dawes.hernandezsanchezrodrigopracticaexamen1.modelo.Juego;
import com.dawes.hernandezsanchezrodrigopracticaexamen1.modelo.Usuario;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Random;

@Controller
public class ControladorPrincipal {
    @RequestMapping("/perfil")
    public String perfil(Model model){
        LocalDate fechaRegistro = LocalDate.of(2016, 5, 15);

        Usuario usuario = new Usuario("Rodrigo", "España", fechaRegistro);

        LocalDate fechaActual = LocalDate.now();
        int anoRegistrados = fechaActual.getYear() - usuario.getFechaRegistro().getYear();

        model.addAttribute("usuario", usuario);
        model.addAttribute("fechaRegistro",fechaRegistro);
        model.addAttribute("anoRegistrados", anoRegistrados);

        return "perfil";
    }

    @RequestMapping("/juego")
    public String juego(Model model){
        Juego juego = new Juego("Minecraft", "Aventura", 9.99, 1000);

        model.addAttribute("juego", juego);

        return "juego";
    }

    @RequestMapping("/oferta")
    public String oferta(Model model){
        Juego juego = new Juego("Minecraft", "Aventura", 9.99, 1000);
        model.addAttribute("juego", juego);

        return "oferta";
    }

    @RequestMapping("/biblioteca")
    public String biblioteca(Model model){
        Biblioteca biblioteca = new Biblioteca
    }
}
