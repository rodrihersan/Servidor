package com.aagpazos.bibliotecavideojuegospractica.controller.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.ResumenDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasRestController {

    private final PrestamoService prestamoService;

    public EstadisticasRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // RF-14: Consultar estadísticas
    @GetMapping
    public ResponseEntity<ResumenDTO> resumen() {
        return ResponseEntity.ok(prestamoService.resumen());
    }
}
