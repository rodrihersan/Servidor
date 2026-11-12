package com.example.bibliotecavideojuegos.controlador;

import com.example.bibliotecavideojuegos.exception.ReglaNegocioException;
import com.example.bibliotecavideojuegos.service.PrestamoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/prestamos")
public class PrestamoControlador {

    private final PrestamoService prestamoService;

    public PrestamoControlador(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // ---------- REGISTRAR PRÉSTAMO ----------

    @GetMapping("/registrar")
    public String mostrarFormulario(Model model) {

        model.addAttribute("desplegableSocios", prestamoService.obtenerDesplegableSocios());
        model.addAttribute("desplegableCopias", prestamoService.obtenerDesplegableCopias());

        return "prestamos/registrar";
    }

    @PostMapping("/registrar")
    public String insertarPrestamo(@RequestParam int idSocio,
                                   @RequestParam int idCopia,
                                   Model model) {

        try {
            LocalDate fechaLimite = prestamoService.insertarPrestamo(idSocio, idCopia);
            model.addAttribute("mensaje", "Préstamo registrado correctamente. Fecha límite: " + fechaLimite);

        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("idSocioSeleccionado", idSocio);
            model.addAttribute("idCopiaSeleccionada", idCopia);
        }

        model.addAttribute("desplegableSocios", prestamoService.obtenerDesplegableSocios());
        model.addAttribute("desplegableCopias", prestamoService.obtenerDesplegableCopias());

        return "prestamos/registrar";
    }

    // ---------- LISTAR PRÉSTAMOS ----------

    @GetMapping("/listado")
    public String mostrarListado(Model model) {

        model.addAttribute("prestamos", prestamoService.listarPrestamos("", "", "", null));

        return "prestamos/listado";
    }

    @PostMapping("/listado")
    public String filtrarListado(@RequestParam(defaultValue = "") String socio,
                                 @RequestParam(defaultValue = "") String videojuego,
                                 @RequestParam(defaultValue = "") String estado,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                 Model model) {

        model.addAttribute("prestamos", prestamoService.listarPrestamos(socio, videojuego, estado, fechaDesde));

        return "prestamos/listado";
    }

    // ---------- RENOVAR PRÉSTAMO ----------

    @GetMapping("/renovar")
    public String mostrarRenovacion(Model model) {

        model.addAttribute("prestamos", prestamoService.listarPrestamosAbiertos());

        return "prestamos/renovar";
    }

    @PostMapping("/renovar")
    public String renovarPrestamo(@RequestParam int idPrestamo, Model model) {

        try {
            LocalDate nuevaFechaLimite = prestamoService.renovarPrestamo(idPrestamo);

            model.addAttribute("mensaje",
                    "Préstamo renovado correctamente. Nueva fecha límite: " + nuevaFechaLimite);

        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("prestamos", prestamoService.listarPrestamosAbiertos());

        return "prestamos/renovar";
    }

    // ---------- INFORME DE LOS CINCO VIDEOJUEGOS MÁS PRESTADOS ----------

    @GetMapping("/informe")
    public String mostrarInforme(Model model) {

        model.addAttribute("videojuegos", prestamoService.obtenerCincoVideojuegosMasPrestados());

        return "prestamos/informe";
    }

    // ---------- DEVOLVER PRÉSTAMO ----------

    @GetMapping("/devolver")
    public String mostrarDevolucion(Model model) {

        model.addAttribute("prestamos", prestamoService.listarPrestamosAbiertos());

        return "prestamos/devolver";
    }

    @PostMapping("/devolver")
    public String devolverPrestamo(@RequestParam int idPrestamo,
                                   @RequestParam(defaultValue = "false") boolean danada,
                                   Model model) {

        try {
            prestamoService.devolverPrestamo(idPrestamo, danada);
            model.addAttribute("mensaje", "Préstamo devuelto correctamente.");

        } catch (ReglaNegocioException e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("prestamos", prestamoService.listarPrestamosAbiertos());

        return "prestamos/devolver";
    }
}