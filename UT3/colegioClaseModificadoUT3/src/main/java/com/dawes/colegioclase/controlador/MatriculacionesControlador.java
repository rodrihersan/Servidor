package com.dawes.colegioclase.controlador;

import com.dawes.colegioclase.dto.MatriculacionDTO;
import com.dawes.colegioclase.service.MatriculacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/matriculaciones")
public class MatriculacionesControlador {
    private final MatriculacionService matriculacionService;

    public MatriculacionesControlador(MatriculacionService
                                           matriculacionService) {
        this.matriculacionService = matriculacionService;
    }

    @GetMapping("/insertar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("matriculacion", new MatriculacionDTO());
        model.addAttribute("desplegableAlumnos",
                matriculacionService.obtenerDesplegableAlumnos());
        model.addAttribute("desplegableAsignaturas",
                matriculacionService.obtenerDesplegableAsignaturas());
        return "matriculaciones/insertar";
    }

    @PostMapping("/insertar")
    public String insertarMatriculacion(@ModelAttribute("matriculacion") MatriculacionDTO matriculacion,
                          Model model) {
        Optional<Double> tasaPagada =
                matriculacionService.insertarMatriculacion(
                        matriculacion.getIdAlumno(),
                        matriculacion.getIdAsignatura(), matriculacion.getFecha());
        model.addAttribute("tasaPagada", tasaPagada.orElse(null));
        model.addAttribute("erroInsercion", tasaPagada.isEmpty());
        model.addAttribute("matriculacion", new MatriculacionDTO());
        model.addAttribute("desplegableAlumnos",
                matriculacionService.obtenerDesplegableAlumnos());
        model.addAttribute("desplegableAsignaturas",
                matriculacionService.obtenerDesplegableAsignaturas());
        return "matriculaciones/insertar";
    }

}
