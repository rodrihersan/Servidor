package com.dawes.hernandezsanchezrodrigo_biblioteca.dto;

public class CopiaDTO {
    private Integer id;
    private String  codigoInventario;
    private Integer idPlataforma;
    private String estado;
    private boolean activado;

    public CopiaDTO(int id, String codigoInventario, int idPlataforma, String estado, boolean activado) {
        this.id = id;
        this.codigoInventario = codigoInventario;
        this.idPlataforma = idPlataforma;
        this.estado = estado;
        this.activado = activado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public int getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(int idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
}
