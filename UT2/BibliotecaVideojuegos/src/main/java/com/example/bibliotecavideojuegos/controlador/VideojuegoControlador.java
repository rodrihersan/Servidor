package com.example.bibliotecavideojuegos.controlador;


import com.example.bibliotecavideojuegos.dto.VideojuegoDTO;
import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import com.example.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoControlador {
    private final VideojuegoService videojuegoService;

    public VideojuegoControlador(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping("/listadoVideojuegos")
    public String listadoVideojuegos(Model model) {
        ArrayList<VideojuegoDTO> listaVideojuegos = videojuegoService.filtrarVideojuegos(null, null, null, null, null);
        model.addAttribute("listaVideojuegos", listaVideojuegos);
        model.addAttribute("desplegableDesarrolladoras", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/listado";
    }

    @PostMapping("/listadoVideojuegos")
    public String buscarVideojuegos(@RequestParam String titulo,
                                    @RequestParam(required = false) Integer idDesarrolladora,
                                    @RequestParam(required = false) Integer anioDesde,
                                    @RequestParam(required = false) Integer pegiMaximo,
                                    @RequestParam(required = false) Boolean soloDisponibles,
                                    Model model) {
        ArrayList<VideojuegoDTO> listaVideojuegos = videojuegoService.filtrarVideojuegos(titulo, idDesarrolladora, anioDesde, pegiMaximo, soloDisponibles);
        model.addAttribute("desplegableDesarrolladoras", videojuegoService.obtenerDesplegableDesarrolladoras());
        model.addAttribute("listaVideojuegos", listaVideojuegos);
        return "videojuegos/listado";
    }

    @GetMapping("/insertarVideojuego")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("videojuego", new VideojuegoDTO());
        model.addAttribute("desplegableDesarrolladoras", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/insertar";
    }

    @PostMapping("/insertarVideojuego")
    public String insertarVideojuego(@ModelAttribute("videojuego") VideojuegoDTO videojuego, Model model) {
        try {
            videojuegoService.insertarVideojuego(videojuego);
            model.addAttribute("mensaje", "Videojuego " + videojuego.getTitulo() + " insertado correctamente.");
            model.addAttribute("videojuego", new VideojuegoDTO());
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("desplegableDesarrolladoras", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/insertar";
    }
}