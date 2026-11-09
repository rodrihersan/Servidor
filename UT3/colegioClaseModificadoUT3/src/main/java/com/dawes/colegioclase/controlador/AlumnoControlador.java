package com.dawes.colegioclase.controlador;

import com.dawes.colegioclase.dto.AlumnoDTO;
import com.dawes.colegioclase.excepcion.ReglaNegocioException;
import com.dawes.colegioclase.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/alumnos")
public class AlumnoControlador {
    private final AlumnoService alumnoService;

    public AlumnoControlador(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/listadoAlumnos")
    public String listadoAlumnos(Model model) {
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.filtrarAlumnos(null, null, null, null, null);
        model.addAttribute("listaAlumnos", listaAlumnos);
        model.addAttribute("desplegableMunicipios",
                alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/listado";
    }

    @PostMapping("/listadoAlumnos")
    public String buscarAlumnos(@RequestParam String nombre,
                                @RequestParam String apellido,
                                @RequestParam(required = false) Integer idMunicipio,
                                @RequestParam(required = false) Boolean familiaNumerosa,
                                @RequestParam(required = false) Boolean activo,
                                Model model) {

        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.filtrarAlumnos(nombre, apellido, idMunicipio, familiaNumerosa, activo);
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        model.addAttribute("listaAlumnos", listaAlumnos);
        return "alumnos/listado";
    }


    //INSERTAR
    @GetMapping("/insertarAlumno")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("alumno", new AlumnoDTO());
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/insertar";
    }

    @PostMapping("/insertarAlumno")
    public String insertarAlumno(@ModelAttribute("alumno") AlumnoDTO alumno, Model model) {
        try {
            alumnoService.insertarAlumno(alumno);
            model.addAttribute("mensaje", "Alumno " + alumno.getNombre() + " " + alumno.getApellido() + " insertado correctamente.");
            model.addAttribute("alumno", new AlumnoDTO());
        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/insertar";
    }

    //MODIFICAR
    @GetMapping("/formularioActualizarAlumnos")
    public String mostrarBusquedaModificar(Model model) {
        return "alumnos/modificar";
    }

    @PostMapping("/formularioActualizarAlumnos")
    public String buscarParaModificar(@RequestParam String nombre,
                                      @RequestParam String apellido,
                                      @RequestParam(required = false) Boolean familiaNumerosa,
                                      @RequestParam(required = false) Boolean activo,
                                      Model model) {
        Boolean filtroFamiliaNumerosa = familiaNumerosa != null && familiaNumerosa;
        Boolean filtroActivo = activo != null && activo;
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.buscarAlumnos(nombre, apellido, filtroFamiliaNumerosa, filtroActivo);
        model.addAttribute("listaAlumnos", listaAlumnos);
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        model.addAttribute("alumno", new AlumnoDTO());
        return "alumnos/modificar";
    }

    @PostMapping("/{id}/modificar")
    public String actualizarAlumno(@PathVariable Integer id, @ModelAttribute("alumno") AlumnoDTO alumno, Model model) {
        alumno.setId(id);
        alumno.setFamiliaNumerosa(alumno.getFamiliaNumerosa() != null && alumno.getFamiliaNumerosa() );
        alumno.setActivo(alumno.getActivo() != null && alumno.getActivo() );

        int filasAfectadas = alumnoService.actualizarAlumno(alumno);
        model.addAttribute("resultado", filasAfectadas == 1);
        model.addAttribute("desplegableMunicipios", alumnoService.obtenerDesplegableMunicipios());
        return "alumnos/modificar";
    }

    //BORRAR
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
        ArrayList<AlumnoDTO> listaAlumnos = alumnoService.buscarAlumnos(nombre, apellido, filtroFamiliaNumerosa, filtroActivo);
        model.addAttribute("listaAlumnos", listaAlumnos);
        return "alumnos/borrar";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarAlumno(@PathVariable Integer id, Model model) {
        int filasAfectadas = alumnoService.eliminarAlumno(id);
        model.addAttribute("resultado", filasAfectadas == 1);
        return "alumnos/borrar";
    }

    @PostMapping("/alumnos")
    public ResponseEntity<AlumnoDTO> insertarAlumno(@RequestBody AlumnoDTO
                                                            alumno) {
        AlumnoDTO alumnoInsertado = alumnoService.insertarAlumnoRest(alumno);
        return new ResponseEntity<>(alumnoInsertado, HttpStatus.CREATED);
    }

}

