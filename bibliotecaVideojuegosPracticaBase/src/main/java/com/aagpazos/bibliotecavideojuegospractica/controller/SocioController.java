package com.aagpazos.bibliotecavideojuegospractica.controller;

import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.ReglaNegocioException;
import com.aagpazos.bibliotecavideojuegospractica.service.SocioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    // ---------- RF-05 Listar ----------
    @GetMapping("/listado")
    public String listado(Model model) {
        SocioDTO filtro = new SocioDTO(null, null, null);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/listado";
    }

    /**
     * activo llega como "", "true" o "false" desde el desplegable (vacío = todos).
     */
    @PostMapping("/listado")
    public String buscar(@RequestParam(required = false) String nombre,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) Boolean activo,
                         Model model) {
        SocioDTO filtro = new SocioDTO(nombre, email, activo);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/listado";
    }

    // ---------- RF-06 Insertar ----------
    @GetMapping("/insertar")
    public String formularioInsertar() {
        return "socios/insertar";
    }

    @PostMapping("/insertar")
    public String insertar(@RequestParam String nombre,
                           @RequestParam String apellidos,
                           @RequestParam String email,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
                           Model model) {
        try {
            var creado = socioService.crear(new SocioDTO(nombre, apellidos, email, fechaNacimiento, null));
            model.addAttribute("mensaje", "Socio " + creado.getNombre() + " " + creado.getNombre() + " dado de alta correctamente (id " + creado.getId() + ")");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException |
                 DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        return "socios/insertar";
    }

    // ---------- RF-07 Modificar ----------
    @GetMapping("/modificar")
    public String formularioModificar(Model model) {
        SocioDTO filtro = new SocioDTO(null, null, null);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/modificar";
    }

    @PostMapping("/modificar")
    public String buscarParaModificar(@RequestParam(required = false) String nombre,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false) Boolean activo,
                                      Model model) {
        boolean filtroActivo = activo != null && activo;
        SocioDTO filtro = new SocioDTO(nombre, email, filtroActivo);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/modificar";
    }

    /**
     * El checkbox "activo" desmarcado no llega: null = false (es una baja).
     */
    @PostMapping("/actualizar")
    public String actualizar(@RequestParam int id,
                             @RequestParam String nombre,
                             @RequestParam String apellidos,
                             @RequestParam String email,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
                             @RequestParam(required = false) Boolean activo,
                             Model model) {
        try {
            boolean filtroActivo = activo != null && activo;
            socioService.actualizar(id, new SocioDTO(nombre, apellidos, email, fechaNacimiento,filtroActivo));
            model.addAttribute("mensaje", "Socio " + id + " modificado correctamente");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException |
                 DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        SocioDTO filtro = new SocioDTO(null, null, null);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/modificar";
    }

    // ---------- RF-08 Borrar ----------
    @GetMapping("/borrar")
    public String formularioBorrar(Model model) {
        SocioDTO filtro = new SocioDTO(null, null, null);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/borrar";
    }

    @PostMapping("/borrar")
    public String buscarParaBorrar(@RequestParam(required = false) String nombre,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) Boolean activo,
                                   Model model) {
        boolean filtroActivo = activo != null && activo;
        SocioDTO filtro = new SocioDTO(nombre, email, filtroActivo);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/borrar";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam int id, Model model) {
        try {
            socioService.borrar(id);
            model.addAttribute("mensaje", "Socio " + id + " borrado correctamente");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException |
                 DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        SocioDTO filtro = new SocioDTO(null, null, null);
        model.addAttribute("listaSocios", socioService.listar(filtro));
        return "socios/borrar";
    }
}
