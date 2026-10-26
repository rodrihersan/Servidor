package com.dawes.hernandezsanchezrodrigo_biblioteca.dto;

public class DesarrolladoraDTO {
    private String nombre;
    private String pais;
    private Integer id;

    public DesarrolladoraDTO(String nombre, String pais, int id) {
        this.nombre = nombre;
        this.pais = pais;
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
