package com.aagpazos.bibliotecavideojuegospractica.controller;

import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InicioController {
    private final PrestamoService prestamoService;

    public InicioController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("resumen", prestamoService.resumen());
        return "inicio";
    }
}
