package com.aagpazos.bibliotecavideojuegospractica.dao.interfaces;

import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;

import java.util.ArrayList;

public interface ISocioDAO {
    ArrayList<SocioDTO> buscar(SocioDTO filtro);
    SocioDTO buscarPorId(int id);
    boolean existeEmail(String email);
    int insertar(SocioDTO socio);
    void actualizar(SocioDTO socio);
    void borrar(int id);
    int contarPrestamos(int idSocio);
}
