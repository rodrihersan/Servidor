package com.aagpazos.bibliotecavideojuegospractica.controller;

import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.ReglaNegocioException;
import com.aagpazos.bibliotecavideojuegospractica.service.CopiaService;
import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import com.aagpazos.bibliotecavideojuegospractica.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    private static final List<String> ESTADOS = List.of("EN_PLAZO", "VENCIDO", "DEVUELTO");

    private final PrestamoService prestamoService;
    private final SocioService socioService;
    private final CopiaService copiaService;

    public PrestamoController(PrestamoService prestamoService, SocioService socioService, CopiaService copiaService) {
        this.prestamoService = prestamoService;
        this.socioService = socioService;
        this.copiaService = copiaService;
    }

    // ---------- RF-05 Registrar ----------
    @GetMapping("/registrar")
    public String formularioRegistrar(Model model) {
        cargarDesplegablesRegistro(model);
        return "prestamos/registrar";
    }

    /** Las reglas de negocio las aplica el Service; aquí solo se muestra el resultado (o el motivo del rechazo). */
    @PostMapping("/registrar")
    public String registrar(@RequestParam(required = false) Integer idSocio,
                            @RequestParam(required = false) Integer idCopia,
                            Model model) {
        try {
            model.addAttribute("prestamoCreado", prestamoService.registrar(new PrestamoDTO(idSocio, idCopia)));
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException | DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        cargarDesplegablesRegistro(model);
        return "prestamos/registrar";
    }

    // ---------- RF-12 Listar ----------
    @GetMapping("/listado")
    public String listado(Model model) {
        return cargarListado(model, new PrestamoDTO(null, null, null, null));
    }

    @PostMapping("/listado")
    public String buscar(@RequestParam(required = false) String socio,
                         @RequestParam(required = false) String titulo,
                         @RequestParam(required = false) String estado,
                         @RequestParam(required = false) LocalDate desde,
                         Model model) {
        try {
            return cargarListado(model, new PrestamoDTO(socio, titulo, estado, desde));
        } catch (DatosInvalidosException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaPrestamos", List.of());
            model.addAttribute("estados", ESTADOS);
            return "prestamos/listado";
        }
    }

    // ---------- RF-13 Devolver ----------
    @GetMapping("/devolver")
    public String formularioDevolver(Model model) {
        model.addAttribute("listaAbiertos", prestamoService.listar(new PrestamoDTO(null, null, "ABIERTO", null)));
        return "prestamos/devolver";
    }

    @PostMapping("/devolver")
    public String buscarAbiertos(@RequestParam(required = false) String socio, Model model) {
        model.addAttribute("listaAbiertos", prestamoService.listar(new PrestamoDTO(socio, null, "ABIERTO", null)));
        return "prestamos/devolver";
    }

    @PostMapping("/confirmarDevolucion")
    public String confirmarDevolucion(@RequestParam int idPrestamo,
                                      @RequestParam(required = false) Boolean copiaDanada,
                                      Model model) {
        try {
            model.addAttribute("prestamoDevuelto",
                    prestamoService.devolver(idPrestamo, copiaDanada));
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException | DatosInvalidosException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("listaAbiertos", prestamoService.listar(new PrestamoDTO(null, null, "ABIERTO", null)));
        return "prestamos/devolver";
    }

    //-- DEFENSA: RENOVAR Y PRESTAMOS
    @PostMapping("/renovar")
    public String renovar(@RequestParam int idPrestamo, Model model) {
        try {
            PrestamoDTO renovado = prestamoService.renovar(idPrestamo);
            model.addAttribute("mensaje", "Préstamo " + idPrestamo
                    + " renovado. Nueva fecha límite: " + renovado.getFechaLimite());
        } catch (ReglaNegocioException | ConflictoException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("listaPrestamos", prestamoService.listar(new PrestamoDTO(null, null, null, null)));
        return "prestamos/listado";
    }

    @GetMapping("/mas-prestados")
    public String masPrestados(Model model) {
        model.addAttribute("listaVideojuegos", prestamoService.masPrestados());
        return "prestamos/mas-prestados";
    }



    // ---------- Auxiliares ----------
    private String cargarListado(Model model, PrestamoDTO filtro) {
        model.addAttribute("listaPrestamos", prestamoService.listar(filtro));
        model.addAttribute("estados", ESTADOS);
        return "prestamos/listado";
    }

    /** Solo socios activos y copias disponibles. Aun así el Service vuelve a validar RN-1 y RN-2. */
    private void cargarDesplegablesRegistro(Model model) {
        model.addAttribute("desplegableSocios", socioService.listar(new SocioDTO(null, null, true)));
        model.addAttribute("desplegableCopias", copiaService.listarDisponbles());
    }

}
