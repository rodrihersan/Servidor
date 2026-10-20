package com.dawes.colegio.controlador;

import com.dawes.colegio.dto.AlumnoDTO;
import com.dawes.colegio.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/alumnos")
public class AlumnoControlador {
    private AlumnoService alumnoService;

    public AlumnoControlador(AlumnoService alumnoService){
        this.alumnoService = alumnoService;
    }

    @GetMapping("/listadoAlumnos")
    public String listadoAlumnos(Model model) {
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.buscarAlumnos("", "", false, true);
        model.addAttribute("listaAlumnos", listaAlumnos);
        return "alumnos/listado";
    }
    @PostMapping("/listadoAlumnos")
    public String buscarAlumnos(@RequestParam String nombre,
                                @RequestParam String apellido,
                                @RequestParam(required = false) Boolean familiaNumerosa,
                                @RequestParam(required = false) Boolean activo,
                                Model model) {
// Un checkbox desmarcado no llega como parámetro
        boolean filtroFamiliaNumerosa = false;
        if (familiaNumerosa != null && familiaNumerosa) {
            filtroFamiliaNumerosa = true;
        }
        boolean filtroActivo = false;
        if (activo != null && activo) {
            filtroActivo = true;
        }
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.buscarAlumnos(nombre, apellido, filtroFamiliaNumerosa, filtroActivo);
        model.addAttribute("listaAlumnos", listaAlumnos);
        return "alumnos/listado";
    }
}
