
package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.CopiaDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.CopiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/copias")
public class CopiasRestController {

    private final CopiaService copiaService;

    public CopiasRestController(CopiaService copiaService) {
        this.copiaService = copiaService;
    }

    @Operation(
            summary = "Listar copias disponibles por videojuego",
            description = "Obtiene las copias disponibles de un videojuego."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Copias obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    })
    @GetMapping
    public ResponseEntity<ArrayList<CopiaDTO>> listarDisponiblesPorVideojuego(@RequestParam int idVideojuego) {

        return ResponseEntity.ok(copiaService.listarDisponiblesPorVideojuego(idVideojuego));
    }

    @Operation(
            summary = "Registrar una copia",
            description = "Registra una nueva copia de un videojuego."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Copia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos de la copia no son válidos"),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    })
    @PostMapping
    public ResponseEntity<Integer> insertar(@RequestBody CopiaDTO copia) {

        int id = copiaService.insertar(copia);

        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Modificar estado de una copia",
            description = "Actualiza el estado físico de una copia existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la copia actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "El estado indicado no es válido"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable int id,
            @RequestBody CopiaDTO copia) {

        copiaService.actualizarEstado(id, copia.getEstado());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Modificar parcialmente el estado de una copia",
            description = "Actualiza el estado físico de una copia existente mediante una petición parcial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la copia actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "El estado indicado no es válido"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstadoParcial(
            @PathVariable int id,
            @RequestBody CopiaDTO copia) {

        copiaService.actualizarEstado(id, copia.getEstado());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Eliminar una copia",
            description = "Elimina una copia existente mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Copia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Copia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {

        copiaService.borrar(id);

        return ResponseEntity.ok().build();
    }
}
