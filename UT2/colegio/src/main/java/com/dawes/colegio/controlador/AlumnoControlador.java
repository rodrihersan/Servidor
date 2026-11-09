package com.dawes.colegio.controlador;

import com.dawes.colegio.dto.AlumnoDTO;
import com.dawes.colegio.service.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/insertarAlumno")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("alumno", new AlumnoDTO());
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/insertar";
    }

    @PostMapping("/insertarAlumno")
    public String insertarAlumno(@ModelAttribute("alumno") AlumnoDTO alumno, Model model) {
        int filasAfectadas = alumnoService.insertarAlumno(alumno);
        model.addAttribute("resultado", filasAfectadas == 1);
        model.addAttribute("alumno", new AlumnoDTO());
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/insertar";
    }

    @GetMapping("/formularioActualizarAlumnos")
    public String mostrarBusquedaModificar(Model model) {
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/modificar";
    }

    @PostMapping("/formularioActualizarAlumnos")
    public String buscarParaModificar(@RequestParam String nombre,
                                      @RequestParam String apellido,
                                      @RequestParam(required = false) Boolean familiaNumerosa,
                                      @RequestParam(required = false) Boolean activo,
                                      Model model) {
        boolean filtroFamiliaNumerosa = familiaNumerosa != null && familiaNumerosa;
        boolean filtroActivo = activo != null && activo;
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.buscarAlumnos(nombre, apellido, filtroFamiliaNumerosa, filtroActivo);
        model.addAttribute("listaAlumnos", listaAlumnos);
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        model.addAttribute("alumno", new AlumnoDTO());
        return "alumnos/modificar";
    }

    @PostMapping("/{id}/modificar")
    public String actualizarAlumno(@PathVariable String id,
                                   @ModelAttribute("alumno") AlumnoDTO alumno, Model model) {
        alumno.setId(id);
        int filasAfectadas = alumnoService.actualizarAlumno(alumno);
        model.addAttribute("resultado", filasAfectadas == 1);
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/modificar";
    }
    //PathVariable llega la ruta
    //modelatributte se usa cuando hay un formulario (th object y th field) llega alumnoDTO (objeto entero)
    //requestParam diferentes campos de un formulario

    @GetMapping("/formularioBorrarAlumnos")
    public String mostrarBusquedaBorrar() {
        return "alumnos/borrar";
    }
    @PostMapping("/formularioBorrarAlumnos")
    public String buscarParaBorrar(@RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam(required = false) Boolean familiaNumerosa,
                                   @RequestParam(required = false) Boolean activo,
                                   Model model) {
        boolean filtroFamiliaNumerosa = familiaNumerosa != null && familiaNumerosa;
        boolean filtroActivo = activo != null && activo;
        ArrayList<AlumnoDTO> listaAlumnos =
                alumnoService.buscarAlumnos(nombre, apellido, filtroFamiliaNumerosa, filtroActivo);
        model.addAttribute("listaAlumnos", listaAlumnos);
        return "alumnos/borrar";
    }
    @PostMapping("/{id}/eliminar")
    public String eliminarAlumno(@PathVariable String id, Model model) {
        int filasAfectadas = alumnoService.eliminarAlumno(id);
        model.addAttribute("resultado", filasAfectadas == 1);
        return "alumnos/borrar";
    }

}
