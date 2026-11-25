
package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.SocioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/socios")
public class SocioRestController {

    private final SocioService socioService;

    public SocioRestController(SocioService socioService) {
        this.socioService = socioService;
    }

    // Consultar socios con filtros opcionales
    @GetMapping
    public ResponseEntity<ArrayList<SocioDTO>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean activo) {

        SocioDTO filtro = new SocioDTO(nombre, email, activo);

        return ResponseEntity.ok(socioService.listar(filtro));
    }

    // Consultar un socio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<SocioDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(socioService.obtener(id));
    }

    // Registrar un socio
    @PostMapping
    public ResponseEntity<SocioDTO> crear(@RequestBody SocioDTO socio) {
        SocioDTO nuevo = socioService.crear(socio);
        return ResponseEntity.status(201).body(nuevo);
    }

    // Modificar un socio
    @PutMapping("/{id}")
    public ResponseEntity<SocioDTO> actualizar(
            @PathVariable int id,
            @RequestBody SocioDTO socio) {

        return ResponseEntity.ok(socioService.actualizar(id, socio));
    }

    // Eliminar un socio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        socioService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}