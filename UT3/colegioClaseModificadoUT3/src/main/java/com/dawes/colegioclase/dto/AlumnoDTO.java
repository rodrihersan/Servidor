package com.dawes.colegioclase.dto;

public class AlumnoDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer idMunicipio;
    private String municipio;
    private Boolean familiaNumerosa;
    private Boolean activo;

    public AlumnoDTO() {
    }

    public AlumnoDTO(Integer id, String nombre, String apellido, Integer idMunicipio, String municipio, Boolean familiaNumerosa, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idMunicipio = idMunicipio;
        this.municipio = municipio;
        this.familiaNumerosa = familiaNumerosa;
        this.activo = activo;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Boolean getFamiliaNumerosa() {
        return familiaNumerosa;
    }

    public void setFamiliaNumerosa(Boolean familiaNumerosa) {
        this.familiaNumerosa = familiaNumerosa;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
