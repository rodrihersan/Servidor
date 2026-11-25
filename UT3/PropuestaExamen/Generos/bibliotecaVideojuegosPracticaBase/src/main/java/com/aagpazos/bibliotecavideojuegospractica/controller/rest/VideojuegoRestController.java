package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

//devolucion de datos
@RestController
@RequestMapping("/api/videojuegos")//ruta base
public class VideojuegoRestController {

    private final VideojuegoService videojuegoService;

    public VideojuegoRestController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping //consulta
    public ResponseEntity<ArrayList<VideojuegoDTO>> listar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anioDesde,
            @RequestParam(required = false) Integer pegiMax,
            @RequestParam(required = false) Boolean soloDisponibles,
            @RequestParam(required = false) Integer idGenero) {

        VideojuegoDTO filtro = new VideojuegoDTO(
                titulo,
                idDesarrolladora,
                anioDesde,
                pegiMax,
                soloDisponibles != null && soloDisponibles
        );

        filtro.setIdGenero(idGenero);

        return ResponseEntity.ok(videojuegoService.listar(filtro));
    }

    // Obtener videojuego por ID
    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(videojuegoService.obtenerVideojuegoRest(id));
    }

    // Crear videojuego
    @PostMapping
    public ResponseEntity<VideojuegoDTO> crear(@RequestBody VideojuegoDTO videojuego) { //RequestBody videojuego formato JSON

        VideojuegoDTO nuevo = videojuegoService.insertarVideojuegoRest(videojuego);

        return ResponseEntity.status(201).body(nuevo); //controla HTTP
    }
}
