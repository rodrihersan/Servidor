package com.aagpazos.bibliotecavideojuegospractica.controller;

import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.exception.ConflictoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.DatosInvalidosException;
import com.aagpazos.bibliotecavideojuegospractica.exception.RecursoNoEncontradoException;
import com.aagpazos.bibliotecavideojuegospractica.exception.ReglaNegocioException;
import com.aagpazos.bibliotecavideojuegospractica.service.DesarrolladoraService;
import com.aagpazos.bibliotecavideojuegospractica.service.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {
    private static final List<Integer> PEGIS = List.of(3, 7, 12, 16, 18);

    private final VideojuegoService videojuegoService;
    private final DesarrolladoraService desarrolladoraService;

    public VideojuegoController(VideojuegoService videojuegoService, DesarrolladoraService desarrolladoraService) {
        this.videojuegoService = videojuegoService;
        this.desarrolladoraService = desarrolladoraService;
    }

    // ---------- RF-01 Listar ----------
    @GetMapping("/listado")
    public String listado(Model model) {
        cargarDesplegables(model);
        VideojuegoDTO filtro = new VideojuegoDTO(null, null, null, null, false);
        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/listado";
    }

    @PostMapping("/listado")
    public String buscar(@RequestParam(required = false) String titulo,
                         @RequestParam(required = false) Integer idDesarrolladora,
                         @RequestParam(required = false) Integer anioDesde,
                         @RequestParam(required = false) Integer pegiMax,
                         @RequestParam(required = false) Boolean soloDisponibles,
                         Model model) {
        cargarDesplegables(model);
        /** Un checkbox desmarcado no llega como parámetro: null significa "no marcado". */
        boolean filtrarDisponibles = soloDisponibles != null && soloDisponibles;
        VideojuegoDTO filtro = new VideojuegoDTO(titulo, idDesarrolladora, anioDesde, pegiMax, filtrarDisponibles);

        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/listado";

    }

    // ---------- RF-02 Insertar ----------
    @GetMapping("/insertar")
    public String formularioInsertar(Model model) {
        cargarDesplegables(model);
        return "videojuegos/insertar";
    }

    @PostMapping("/insertar")
    public String insertar(@RequestParam String titulo,
                           @RequestParam(required = false) Integer anioLanzamiento,
                           @RequestParam(required = false) Integer pegi,
                           @RequestParam(required = false) Integer idDesarrolladora,
                           Model model) {
        try {
            var creado = videojuegoService.crear(new VideojuegoDTO(titulo, anioLanzamiento, pegi, idDesarrolladora));
            model.addAttribute("mensaje", "Videojuego '" + creado.getTitulo() + "' insertado correctamente (id " + creado.getId() + ")");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException | DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        cargarDesplegables(model);
        return "videojuegos/insertar";
    }

    // ---------- RF-03 Modificar ----------
    @GetMapping("/modificar")
    public String formularioModificar(Model model) {
        cargarDesplegables(model);
        VideojuegoDTO filtro = new VideojuegoDTO(null, null, null, null, false);
        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/modificar";
    }

    @PostMapping("/modificar")
    public String buscarParaModificar(@RequestParam(required = false) String titulo,
                                      @RequestParam(required = false) Integer idDesarrolladora,
                                      @RequestParam(required = false) Integer anioDesde,
                                      @RequestParam(required = false) Integer pegiMax,
                                      @RequestParam(required = false) Boolean soloDisponibles,
                                      Model model) {
        cargarDesplegables(model);
        /** Un checkbox desmarcado no llega como parámetro: null significa "no marcado". */
        boolean filtrarDisponibles = soloDisponibles != null && soloDisponibles;
        VideojuegoDTO filtro = new VideojuegoDTO(titulo, idDesarrolladora, anioDesde, pegiMax, filtrarDisponibles);

        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/modificar";
    }

    @PostMapping("/actualizar")
    public String actualizar(@RequestParam int id,
                             @RequestParam String titulo,
                             @RequestParam(required = false) Integer anioLanzamiento,
                             @RequestParam(required = false) Integer pegi,
                             @RequestParam(required = false) Integer idDesarrolladora,
                             Model model) {
        try {
            videojuegoService.actualizar(id, new VideojuegoDTO(titulo, anioLanzamiento, pegi, idDesarrolladora));
            model.addAttribute("mensaje", "Videojuego " + id + " modificado correctamente");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException | DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }

        cargarDesplegables(model);
        VideojuegoDTO filtro = new VideojuegoDTO(null, null, null, null, false);

        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/modificar";
    }

    // ---------- RF-04 Borrar ----------
    @GetMapping("/borrar")
    public String formularioBorrar(Model model) {
        cargarDesplegables(model);
        VideojuegoDTO filtro = new VideojuegoDTO(null, null, null, null, false);
        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/borrar";
    }

    @PostMapping("/borrar")
    public String buscarParaBorrar(@RequestParam(required = false) String titulo,
                                   @RequestParam(required = false) Integer idDesarrolladora,
                                   @RequestParam(required = false) Integer anioDesde,
                                   @RequestParam(required = false) Integer pegiMax,
                                   @RequestParam(required = false) Boolean soloDisponibles,
                                   Model model) {
        cargarDesplegables(model);
        /** Un checkbox desmarcado no llega como parámetro: null significa "no marcado". */
        boolean filtrarDisponibles = soloDisponibles != null && soloDisponibles;
        VideojuegoDTO filtro = new VideojuegoDTO(titulo, idDesarrolladora, anioDesde, pegiMax, filtrarDisponibles);

        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));
        return "videojuegos/borrar";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam int id, Model model) {
        try {
            videojuegoService.borrar(id);
            model.addAttribute("mensaje", "Videojuego " + id + " borrado correctamente");
        } catch (ReglaNegocioException | RecursoNoEncontradoException | ConflictoException | DatosInvalidosException e) {
            String mensajeError = e.getMessage();
            if (e instanceof ReglaNegocioException) {
                mensajeError = ((ReglaNegocioException) e).obtenerMensajeUsuario();
            }
            model.addAttribute("error", mensajeError);
        }
        cargarDesplegables(model);
        VideojuegoDTO filtro = new VideojuegoDTO(null, null, null, null, false);

        model.addAttribute("listaVideojuegos", videojuegoService.listar(filtro));

        return "videojuegos/borrar";
    }

    // ---------- Auxiliar ----------
    private void cargarDesplegables(Model model) {
        model.addAttribute("desplegableDesarrolladoras", desarrolladoraService.listarDesarrolladoras());
        model.addAttribute("pegis", PEGIS);
    }
}
