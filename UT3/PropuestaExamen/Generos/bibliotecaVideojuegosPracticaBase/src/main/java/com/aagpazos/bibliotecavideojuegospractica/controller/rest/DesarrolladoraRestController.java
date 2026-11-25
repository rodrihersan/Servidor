
package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.DesarrolladoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraRestController {

    private final DesarrolladoraService desarrolladoraService;

    public DesarrolladoraRestController(DesarrolladoraService desarrolladoraService) {
        this.desarrolladoraService = desarrolladoraService;
    }

    // RF-13: Listar desarrolladoras
    @GetMapping
    public ResponseEntity<ArrayList<DesarrolladoraDTO>> listar() {
        return ResponseEntity.ok(desarrolladoraService.listarDesarrolladoras());
    }
}

