
package com.example.bibliotecavideojuegos.controlador;

import com.example.bibliotecavideojuegos.dto.SocioDTO;
import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import com.example.bibliotecavideojuegos.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/socios")
public class SocioControlador {

    private final SocioService socioService;

    public SocioControlador(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping("/listado")
    public String listadoSocios(Model model) {
        ArrayList<SocioDTO> listaSocios = socioService.filtrarSocios(null, null, null);

        model.addAttribute("listaSocios", listaSocios);

        return "socios/listado";
    }

    @PostMapping("/listado")
    public String buscarSocios(@RequestParam(required = false) String nombre,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) Boolean activo,
                               Model model) {

        ArrayList<SocioDTO> listaSocios = socioService.filtrarSocios(nombre, email, activo);

        model.addAttribute("listaSocios", listaSocios);

        return "socios/listado";
    }

    @GetMapping("/insertar")
    public String mostrarFormularioInsertar() {
        return "socios/insertar";
    }

    @PostMapping("/insertar")
    public String insertarSocio(@RequestParam String nombre,
                                @RequestParam String apellidos,
                                @RequestParam String email,
                                @RequestParam String  fechaNacimiento,
                                Model model) {

        try {
            LocalDate fecha = LocalDate.parse(fechaNacimiento);

            socioService.insertarSocio(nombre, apellidos, email, fecha);

            model.addAttribute("mensaje", "Socio registrado correctamente.");

        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellidos", apellidos);
            model.addAttribute("email", email);
            model.addAttribute("fechaNacimiento", fechaNacimiento);
        }

        return "socios/insertar";
    }
}
