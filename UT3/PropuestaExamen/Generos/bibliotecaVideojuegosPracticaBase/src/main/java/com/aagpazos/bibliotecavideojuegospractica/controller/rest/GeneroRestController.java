
package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.GeneroDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.GeneroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/generos")
public class GeneroRestController {

    private final GeneroService generoService;

    public GeneroRestController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @Operation(
            summary = "Listar géneros",
            description = "Obtiene el listado de todos los géneros de videojuegos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Géneros obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ArrayList<GeneroDTO>> listar() {
        return ResponseEntity.ok(generoService.listarGeneros());
    }

    @Operation(
            summary = "Obtener género por ID",
            description = "Obtiene la información de un género mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(generoService.obtener(id));
    }

    @Operation(
            summary = "Registrar un género",
            description = "Registra un nuevo género de videojuegos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Los datos del género no son válidos")
    })
    @PostMapping
    public ResponseEntity<Integer> insertar(@RequestBody GeneroDTO genero) {
        int id = generoService.insertar(genero);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Modificar un género",
            description = "Actualiza el nombre de un género existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "El nombre indicado no es válido"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable int id,
            @RequestBody GeneroDTO genero) {

        generoService.actualizar(id, genero.getNombre());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Modificar parcialmente un género",
            description = "Actualiza el nombre de un género existente mediante una petición parcial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "El nombre indicado no es válido"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> actualizarParcial(
            @PathVariable int id,
            @RequestBody GeneroDTO genero) {

        generoService.actualizar(id, genero.getNombre());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Eliminar un género",
            description = "Elimina un género existente mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Género eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {

        generoService.borrar(id);

        return ResponseEntity.ok().build();
    }
}
