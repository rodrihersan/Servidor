package com.example.bibliotecavideojuegos.controlador;

import com.example.bibliotecavideojuegos.service.InicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {
    private final InicioService inicioService;

    public InicioControlador(InicioService inicioService) {
        this.inicioService = inicioService;
    }

    @GetMapping("/")
    public String mostrarInicio(Model model) {
        model.addAttribute("totalVideojuegos", inicioService.obtenerTotalVideojuegos());
        model.addAttribute("totalSociosActivos", inicioService.obtenerTotalSociosActivos());
        model.addAttribute("totalPrestamosAbiertos", inicioService.obtenerTotalPrestamosAbiertos());
        model.addAttribute("totalPrestamosVencidos", inicioService.obtenerTotalPrestamosVencidos());
        return "index";
    }
}
