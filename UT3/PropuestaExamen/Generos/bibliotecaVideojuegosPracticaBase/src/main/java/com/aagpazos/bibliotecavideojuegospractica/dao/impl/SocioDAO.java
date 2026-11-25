package com.aagpazos.bibliotecavideojuegospractica.dao.impl;

import com.aagpazos.bibliotecavideojuegospractica.dao.interfaces.ISocioDAO;
import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.repository.SociosRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SocioDAO implements ISocioDAO {
    private final SociosRepository sociosRepository;

    public SocioDAO(SociosRepository sociosRepository) {
        this.sociosRepository = sociosRepository;
    }

    @Override
    public ArrayList<SocioDTO> buscar(SocioDTO filtro) {
        return sociosRepository.buscar(filtro);
    }

    @Override
    public SocioDTO buscarPorId(int id) {
        return sociosRepository.buscarPorId(id);
    }

    @Override
    public boolean existeEmail(String email) {
        return sociosRepository.existeEmail(email);
    }

    @Override
    public int insertar(SocioDTO socio) {
        return sociosRepository.insertar(socio);
    }

    @Override
    public void actualizar(SocioDTO socio) {
        sociosRepository.actualizar(socio);
    }

    @Override
    public void borrar(int id) {
        sociosRepository.borrar(id);
    }

    @Override
    public int contarPrestamos(int idSocio) {
        return sociosRepository.contarPrestamos(idSocio);
    }
}
