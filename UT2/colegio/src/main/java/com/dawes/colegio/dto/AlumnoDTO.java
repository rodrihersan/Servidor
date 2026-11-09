package com.dawes.colegio.dto;

public class AlumnoDTO {
    private String id;
    private String nombre;
    private String apellido;
    private int idMunicipio;
    private String municipio;
    private boolean familiaNumerosa;
    private boolean activo;
    public AlumnoDTO() {
    }
    public AlumnoDTO(String id, String nombre, String apellido, int idMunicipio, String municipio, boolean familiaNumerosa, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idMunicipio = idMunicipio;
        this.municipio = municipio;
        this.familiaNumerosa = familiaNumerosa;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public boolean isFamiliaNumerosa() {
        return familiaNumerosa;
    }

    public void setFamiliaNumerosa(boolean familiaNumerosa) {
        this.familiaNumerosa = familiaNumerosa;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
