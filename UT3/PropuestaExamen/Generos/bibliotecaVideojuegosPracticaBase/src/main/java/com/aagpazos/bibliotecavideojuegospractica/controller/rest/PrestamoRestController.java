
package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoRestController {

    private final PrestamoService prestamoService;

    public PrestamoRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // RF-09: Listar préstamos con filtros
    @Operation(summary = "Listar préstamos", description = "Permite consultar los préstamos aplicando filtros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda incorrectos")
    })
    @GetMapping
    public ResponseEntity<ArrayList<PrestamoDTO>> listar(
            @RequestParam(required = false) String socio,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fechaDesde) {

        PrestamoDTO filtro = new PrestamoDTO(socio, titulo, estado, fechaDesde);

        return ResponseEntity.ok(prestamoService.listar(filtro));
    }

    // RF-10: Obtener un préstamo por ID
    @Operation(summary = "Obtener préstamo", description = "Busca un préstamo mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(prestamoService.obtener(id));
    }

    // RF-11: Registrar un préstamo
    @Operation(summary = "Registrar préstamo", description = "Registra un nuevo préstamo comprobando las reglas de negocio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada incorrectos"),
            @ApiResponse(responseCode = "404", description = "Socio o copia no encontrados"),
            @ApiResponse(responseCode = "422", description = "No se cumplen las reglas de negocio")
    })
    @PostMapping
    public ResponseEntity<PrestamoDTO> registrar(@RequestBody PrestamoDTO prestamo) {
        PrestamoDTO nuevo = prestamoService.registrar(prestamo);
        return ResponseEntity.status(201).body(nuevo);
    }

    // RF-12: Devolver un préstamo
    @Operation(summary = "Devolver préstamo", description = "Registra la devolución de un préstamo e indica si la copia está dañada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto correctamente"),
            @ApiResponse(responseCode = "400", description = "Falta el parámetro copiaDanada o es incorrecto"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
            @ApiResponse(responseCode = "409", description = "El préstamo ya estaba devuelto")
    })
    @PutMapping("/{id}/devolucion")
    public ResponseEntity<PrestamoDTO> devolver(
            @PathVariable int id,
            @RequestParam(name = "copiaDanada") Boolean copiaDañada) {

        prestamoService.devolver(id, copiaDañada);

        return ResponseEntity.ok(prestamoService.obtener(id));
    }
}

