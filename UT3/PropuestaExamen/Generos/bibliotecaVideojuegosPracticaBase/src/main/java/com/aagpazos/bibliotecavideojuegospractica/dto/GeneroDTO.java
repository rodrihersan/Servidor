
package com.aagpazos.bibliotecavideojuegospractica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un género de videojuegos")
public class GeneroDTO {

    @Schema(description = "Identificador del género", example = "1")
    private Integer id;

    @Schema(description = "Nombre del género", example = "Acción")
    private String nombre;

    public GeneroDTO() {}

    public GeneroDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
